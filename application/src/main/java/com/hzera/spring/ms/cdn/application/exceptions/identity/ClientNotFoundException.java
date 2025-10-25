package com.hzera.spring.ms.cdn.application.exceptions.identity;

import com.hzera.spring.ms.cdn.common.domain.HZeraBusinessException;

public class ClientNotFoundException extends HZeraBusinessException {
    public ClientNotFoundException(String message) {
        super(message, "CLIENT_NOT_FOUND");
    }
}
