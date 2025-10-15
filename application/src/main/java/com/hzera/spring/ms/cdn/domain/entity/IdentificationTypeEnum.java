package com.hzera.spring.ms.cdn.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IdentificationTypeEnum {
    DNI("DNI"),
    NIE("NIE");

    private final String value;

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
