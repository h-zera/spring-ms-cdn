package com.hzera.spring.ms.cdn.driver.api.rest.v1.error;

import com.hzera.spring.ms.cdn.application.exceptions.identity.ClientNotFoundException;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.ErrorResource;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.ErrorResourceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class IdentityAPIControllerAdvice {

    //TODO: check exception handling and response status

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ClientNotFoundException.class)
    public ErrorResourceResponse handleClientNotFoundException(ClientNotFoundException exception) {
        ErrorResource error = ErrorResource.builder()
                .build();

        return ErrorResourceResponse.builder().error(error).build();
    }
}
