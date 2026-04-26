package com.spring.job_tracker.ai.dto.requests;


import lombok.*;

import java.util.HashMap;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AiGenerateRequest {
    private String model;
    private List<HashMap<String, String>> input;
    private Integer context_length;
    private Integer temperature;
    private Boolean store;
    private String reasoning;
}

