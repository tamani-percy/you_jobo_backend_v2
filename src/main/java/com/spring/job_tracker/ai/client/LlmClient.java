package com.spring.job_tracker.ai.client;

import com.spring.job_tracker.ai.config.AiPropertiesConfig;
import com.spring.job_tracker.ai.dto.LocalLlmOutputItem;
import com.spring.job_tracker.ai.dto.requests.AiGenerateRequest;
import com.spring.job_tracker.ai.dto.responses.LocalLlmResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.List;

@Component
public class LlmClient {

    private final WebClient aiWebClient;
    private final AiPropertiesConfig aiProperties;
    private final Sinks.Many<String> sink = Sinks.many().multicast().onBackpressureBuffer();

    public LlmClient(WebClient aiWebClient, AiPropertiesConfig aiProperties) {
        this.aiWebClient = aiWebClient;
        this.aiProperties = aiProperties;
    }


    public Flux<String> getEventStream() {
        return sink.asFlux();
    }

    public Mono<String> generate(String prompt, String completionStatus, String errorStatus) {
        AiGenerateRequest request = buildRequest(prompt);

        return aiWebClient.post()
                .uri("/api/v1/chat")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LocalLlmResponse.class)
                .flatMap(response -> Mono.justOrEmpty(
                        response.getOutput().stream()
                                .filter(item -> "message".equalsIgnoreCase(item.getType()))
                                .map(LocalLlmOutputItem::getContent)
                                .filter(content -> content != null && !content.isBlank())
                                .findFirst()
                ))
                .switchIfEmpty(Mono.error(
                        new RuntimeException("No message content found in local LLM response")
                ))
                .doOnSuccess(content -> sink.tryEmitNext(completionStatus))
                .doOnError(e -> sink.tryEmitNext(errorStatus));
    }

    private AiGenerateRequest buildRequest(String prompt) {
        HashMap<String, String> inputPrompt = new HashMap<>();
        inputPrompt.put("type", "text");
        inputPrompt.put("content", prompt);

        return new AiGenerateRequest(
                aiProperties.getModel(),
                List.of(inputPrompt),
                8000,
                0,
                false,
                "on"
        );
    }
}