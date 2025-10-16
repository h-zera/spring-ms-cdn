package com.hzera.spring.ms.cdn.application.ports.driven;

import com.hzera.spring.ms.cdn.domain.entity.identity.ClientEntity;

import java.util.Optional;

public interface IdentityRepositoryPort {
    Optional<ClientEntity> findByClientId(String clientId);
}
