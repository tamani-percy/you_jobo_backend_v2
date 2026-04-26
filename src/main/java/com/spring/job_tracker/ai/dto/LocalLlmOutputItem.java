package com.spring.job_tracker.ai.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalLlmOutputItem {
    private String type;
    private String content;
}