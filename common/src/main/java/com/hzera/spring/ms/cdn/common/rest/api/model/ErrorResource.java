package com.hzera.spring.ms.cdn.common.rest.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResource {
    private String code;
    private String description;
    private List<String> details;
}
