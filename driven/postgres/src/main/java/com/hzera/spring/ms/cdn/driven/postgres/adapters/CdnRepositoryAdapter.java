package com.hzera.spring.ms.cdn.driven.postgres.adapters;

import com.hzera.spring.ms.cdn.application.ports.driven.cdn.CdnRepositoryPort;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnClientConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnResourceEntity;
import com.hzera.spring.ms.cdn.driven.postgres.jpa.cdn.CdnResourceMOJpaRepository;
import com.hzera.spring.ms.cdn.driven.postgres.jpa.cdn.ClientConfigMOJpaRepository;
import com.hzera.spring.ms.cdn.driven.postgres.mappers.cdn.CdnResourceMapper;
import com.hzera.spring.ms.cdn.driven.postgres.mappers.cdn.ClientConfigMapper;
import com.hzera.spring.ms.cdn.driven.postgres.models.cdn.AllowedFeaturesEnum;
import com.hzera.spring.ms.cdn.driven.postgres.models.cdn.ClientConfigMO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CdnRepositoryAdapter implements CdnRepositoryPort {
    private final ClientConfigMOJpaRepository clientConfigMOJpaRepository;
    private final CdnResourceMOJpaRepository cdnResourceMOJpaRepository;

    private final ClientConfigMapper clientConfigMapper;
    private final CdnResourceMapper cdnResourceMapper;

    @Override
    public Optional<CdnClientConfigEntity> findByClientId(String clientId) {
        var clientConfigMO = clientConfigMOJpaRepository.findClientConfigMOByClientId(clientId);

        return clientConfigMapper.toOptionalEntity(clientConfigMO);
    }

    @Override
    public Optional<CdnResourceEntity> findValidResourceByPath(UUID clientConfigId, String path) {
        var resourceMO = cdnResourceMOJpaRepository.findCdnResourceMOByPathAndClientConfig_Id(path, clientConfigId);

        if (resourceMO.isEmpty()) return Optional.empty();

        var resource = resourceMO.get();
        var now = OffsetDateTime.now();
        var expired = resource.getExpiresAt() != null && resource.getExpiresAt().isBefore(now);

        return expired ? Optional.empty() : cdnResourceMapper.toOptionalEntity(resourceMO);
    }

    @Override
    public CdnResourceEntity save(CdnClientConfigEntity clientConfig, CdnResourceEntity cdnResource) {
        var resourceMO = cdnResourceMapper.toModel(cdnResource);
        var queryConfig = new ClientConfigMO();

        queryConfig.setId(clientConfig.getId());
        resourceMO.setClientConfig(queryConfig);

        var savedResourceMO = cdnResourceMOJpaRepository.save(resourceMO);

        return cdnResourceMapper.toEntity(savedResourceMO);
    }
}
