package com.hzera.spring.ms.cdn.driver.api.rest.v1.adapters.cdn.sign;

import com.hzera.spring.ms.cdn.application.ports.driver.CdnServicePort;
import com.hzera.spring.ms.cdn.domain.entity.identity.ClientEntity;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.mappers.CdnDTOMapper;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.CdnSignApi;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.FileSigningRequest;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.FolderSigningRequest;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.SigningResourceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class SignControllerAdapter implements CdnSignApi {
    private final CdnServicePort cdnServicePort;

    private final CdnDTOMapper cdnDTOMapper;

    @Override
    public ResponseEntity<SigningResourceResponse> signCdnFile(FileSigningRequest fileSigningRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        var client = (ClientEntity) securityContext.getAuthentication().getCredentials();

        var configEntity = cdnDTOMapper.toConfigEntity(fileSigningRequest.getConfig());

        var cdnResource = cdnServicePort.getCdnResource(
                client.getClientId(),
                fileSigningRequest.getFilePath(),
                configEntity
        );

        var response = cdnDTOMapper.toSigningResourceResponse(cdnResource);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SigningResourceResponse> signCdnFolder(FolderSigningRequest folderSigningRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        var client = (ClientEntity) securityContext.getAuthentication().getCredentials();

        var configEntity = cdnDTOMapper.toConfigEntity(folderSigningRequest.getConfig());

        var cdnResource = cdnServicePort.getCdnResource(
                client.getClientId(),
                folderSigningRequest.getFolderPath(),
                configEntity
        );

        var response = cdnDTOMapper.toSigningResourceResponse(cdnResource);

        return ResponseEntity.ok(response);
    }
}
