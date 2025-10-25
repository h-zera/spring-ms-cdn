package com.hzera.spring.ms.cdn.driver.api.rest.v1.error;

import com.hzera.spring.ms.cdn.application.exceptions.cdn.CdnClientResourceAccessDenied;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.ErrorResource;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.ErrorResourceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@Slf4j
@RestControllerAdvice
public class CdnAPIControllerAdvice {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CdnClientResourceAccessDenied.class)
    public ErrorResourceResponse handleResourceAccessException(CdnClientResourceAccessDenied error) {
        ErrorResource errorResource = ErrorResource.builder()
                .code(error.getErrorCode())
                .description(error.getMessage())
                .build();

        return ErrorResourceResponse.builder().error(errorResource).build();
    }
}
