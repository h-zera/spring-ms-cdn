package com.hzera.spring.ms.cdn.application.ports.driven.cdn;

import com.hzera.spring.ms.cdn.common.interfaces.HZACacheRepository;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnCustomConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnResourceEntity;

import java.util.Optional;

public interface CdnCachePort extends HZACacheRepository<CdnResourceEntity> {
    String buildKey(String clientId, String path, CdnCustomConfigEntity config);

    default Optional<CdnResourceEntity> get(String clientId, String path, CdnCustomConfigEntity config) {
        var key = buildKey(clientId, path, config);
        return get(key);
    }

    default void save(String clientId, String path, CdnResourceEntity value, CdnCustomConfigEntity config, long ttlInSeconds) {
        var key = buildKey(clientId, path, config);
        save(key, value, ttlInSeconds);
    }
}
