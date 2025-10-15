package com.hzera.spring.ms.cdn.common.rest.api.model;

import lombok.Getter;
import lombok.Setter;

public abstract class ApiObjectResponse<T> {
    @Getter
    @Setter
    private T data;

    public ApiObjectResponse(T data) {
        this.data = data;
    }
}
