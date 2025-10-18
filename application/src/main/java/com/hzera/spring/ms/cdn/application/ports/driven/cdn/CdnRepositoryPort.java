package com.hzera.spring.ms.cdn.application.ports.driven.cdn;

import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnClientConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnResourceEntity;

import java.util.Optional;
import java.util.UUID;

public interface CdnRepositoryPort {
    Optional<CdnClientConfigEntity> findByClientId(String clientId);

    Optional<CdnResourceEntity> findValidResourceByPath(UUID clientConfigId, String path);

    CdnResourceEntity save(CdnClientConfigEntity clientConfig, CdnResourceEntity cdnResource);
}