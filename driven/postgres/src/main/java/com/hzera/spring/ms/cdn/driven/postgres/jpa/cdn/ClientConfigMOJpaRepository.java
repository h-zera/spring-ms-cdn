package com.hzera.spring.ms.cdn.driven.postgres.jpa.cdn;

import com.hzera.spring.ms.cdn.driven.postgres.models.cdn.ClientConfigMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientConfigMOJpaRepository extends JpaRepository<ClientConfigMO, UUID> {
    Optional<ClientConfigMO> findClientConfigMOByClientId(String clientId);
}
