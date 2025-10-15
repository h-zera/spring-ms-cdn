package com.hzera.spring.ms.cdn.application.exceptions;

import com.hzera.spring.ms.cdn.common.domain.HZeraBusinessException;

public class ExampleNotFoundException extends HZeraBusinessException {

    public ExampleNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }
}
