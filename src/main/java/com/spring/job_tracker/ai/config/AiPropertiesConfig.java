package com.spring.job_tracker.ai.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "ai.local")
public class AiPropertiesConfig {
    private String baseUrl;
    private String model;
    private String apiKey;
}