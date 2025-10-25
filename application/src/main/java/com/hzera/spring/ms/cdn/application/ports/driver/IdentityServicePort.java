package com.hzera.spring.ms.cdn.application.ports.driver;

import com.hzera.spring.ms.cdn.application.exceptions.identity.ClientNotFoundException;
import com.hzera.spring.ms.cdn.domain.entity.identity.ClientEntity;

public interface IdentityServicePort {
    ClientEntity getClient(String clientId) throws ClientNotFoundException;
}
