package com.hzera.spring.ms.cdn.driven.postgres.jpa;

import com.hzera.spring.ms.cdn.driven.postgres.models.client.ClientMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientMOJpaRepository extends JpaRepository<ClientMO, Integer> {
    Optional<ClientMO> findClientMOByClientId(String clientId);
}
