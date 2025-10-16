package com.hzera.spring.ms.cdn.driven.postgres.adapters;

import com.hzera.spring.ms.cdn.application.ports.driven.IdentityRepositoryPort;
import com.hzera.spring.ms.cdn.domain.entity.identity.ClientEntity;
import com.hzera.spring.ms.cdn.driven.postgres.jpa.ClientMOJpaRepository;
import com.hzera.spring.ms.cdn.driven.postgres.mappers.client.ClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdentityRepositoryAdapter implements IdentityRepositoryPort {
    private final ClientMOJpaRepository clientMOJpaRepository;

    private final ClientMapper clientMapper;

    @Override
    public Optional<ClientEntity> findByClientId(String clientId) {
        var clientMO = clientMOJpaRepository.findClientMOByClientId(clientId);

        return clientMapper.toOptionalEntity(clientMO);
    }
}
