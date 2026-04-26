package com.spring.job_tracker.ai.dto.responses;

import com.spring.job_tracker.ai.dto.LocalLlmOutputItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocalLlmResponse {
    private String model_instance_id;
    private List<LocalLlmOutputItem> output;
}