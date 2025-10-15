package com.hzera.spring.ms.cdn.common.rest.api.error;

import com.hzera.spring.ms.cdn.common.domain.PageRequestSortException;
import com.hzera.spring.ms.cdn.common.rest.api.model.ErrorResource;
import com.hzera.spring.ms.cdn.common.rest.api.model.ErrorResourceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice(basePackages = {
        "com.hzera.spring.ms.cdn.driver.api"
})
public class CommonAPIControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PropertyReferenceException.class)
    public ErrorResourceResponse handleSortRequestException(PropertyReferenceException exception) {
        var error = new PageRequestSortException();

        ErrorResource errorResource = ErrorResource.builder()
                .code(error.getErrorCode())
                .description(error.getMessage())
                .details(Arrays.asList(
                        error.getMessage(),
                        "Currently the valid sort query format is -/+paramName comma separated for multiple sort"
                ))
                .build();

        return new ErrorResourceResponse(errorResource);
    }

}
