package com.hzera.spring.ms.cdn.driven.postgres.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IdentificationTypeMOEnum {
    DNI("DNI"),
    NIE("NIE");

    private final String value;

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
