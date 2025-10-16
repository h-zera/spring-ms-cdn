package com.hzera.spring.ms.cdn.application.services;

import com.hzera.spring.ms.cdn.application.exceptions.ClientNotFoundException;
import com.hzera.spring.ms.cdn.application.ports.driven.IdentityRepositoryPort;
import com.hzera.spring.ms.cdn.application.ports.driver.IdentityServicePort;
import com.hzera.spring.ms.cdn.domain.entity.identity.ClientEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdentityServiceUseCase implements IdentityServicePort {
    private final IdentityRepositoryPort identityRepository;

    @Override
    public ClientEntity getClient(String clientId) {
        return identityRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Requested client %s not found.", clientId)));
    }
}
