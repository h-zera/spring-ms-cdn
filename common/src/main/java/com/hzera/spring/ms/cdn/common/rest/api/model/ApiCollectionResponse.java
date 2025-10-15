package com.hzera.spring.ms.cdn.common.rest.api.model;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ApiCollectionResponse<T> {
    private List<T> data;
    private HZeraPageResponse pagination;
}
