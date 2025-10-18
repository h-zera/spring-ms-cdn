package com.hzera.spring.ms.cdn.driven.postgres.adapters;

import com.hzera.spring.ms.cdn.application.ports.driven.cdn.CdnRepositoryPort;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnClientConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnResourceEntity;
import com.hzera.spring.ms.cdn.driven.postgres.jpa.cdn.ClientConfigMOJpaRepository;
import com.hzera.spring.ms.cdn.driven.postgres.mappers.cdn.ClientConfigMapper;
import com.hzera.spring.ms.cdn.driven.postgres.models.cdn.AllowedFeaturesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CdnRepositoryAdapter implements CdnRepositoryPort {
    private final ClientConfigMOJpaRepository clientConfigMOJpaRepository;

    private final ClientConfigMapper clientConfigMapper;

    @Override
    public Optional<CdnClientConfigEntity> findByClientId(String clientId) {
        var clientConfigMO = clientConfigMOJpaRepository.findClientConfigMOByClientId(clientId);

        return clientConfigMapper.toOptionalEntity(clientConfigMO);
    }

    @Override
    public Optional<CdnResourceEntity> findValidResourceByPath(UUID clientConfigId, String path) {
        //TODO: Implement actual DB lookup
        return Optional.empty();
    }

    @Override
    public CdnResourceEntity save(CdnClientConfigEntity clientConfig, CdnResourceEntity cdnResource) {
        //TODO: Implement actual save logic
        return cdnResource;
    }
}
