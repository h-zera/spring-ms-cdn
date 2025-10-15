package com.hzera.spring.ms.cdn.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExampleEntity {
    private Long id;
    private String name;
    private String description;
    private OffsetDateTime creationTime;
    private IdentificationTypeEnum identificationType;
    private String identification;
}
