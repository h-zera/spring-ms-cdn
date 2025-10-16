package com.hzera.spring.ms.cdn.driver.api.rest.v1.adapters.cdn.sign;

import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.CdnSignApi;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.FolderSigningRequest;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.SigningResourceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class SignControllerAdapter implements CdnSignApi {
    @Override
    public ResponseEntity<SigningResourceResponse> signCdnFolder(FolderSigningRequest folderSigningRequest) {
        return CdnSignApi.super.signCdnFolder(folderSigningRequest);
    }
}
