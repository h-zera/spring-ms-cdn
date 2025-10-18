package com.hzera.spring.ms.cdn.application.exceptions.cdn;

import com.hzera.spring.ms.cdn.common.domain.HZeraBusinessException;

public class CdnClientResourceAccessDenied extends HZeraBusinessException {
    public CdnClientResourceAccessDenied(String message) {
        super(message, "CLIENT_RESOURCE_ACCESS_DENIED");
    }
}
