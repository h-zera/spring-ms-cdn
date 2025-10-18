package com.hzera.spring.ms.cdn.driven.postgres.jpa.cdn;

import com.hzera.spring.ms.cdn.driven.postgres.models.cdn.CdnResourceMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CdnResourceMOJpaRepository extends JpaRepository<CdnResourceMO, String> {
    Optional<CdnResourceMO> findCdnResourceMOByPathAndClientConfig_Id(String path, UUID configId);
}
