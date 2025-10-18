package com.hzera.spring.ms.cdn.application.ports.driven.cdn;

import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnCustomConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnDefaultConfigEntity;

import java.util.Map;
import java.util.Set;

public interface CdnSecureUrlProviderPort {
    Map<String, String> createQueryMap(
            String resourcePath,
            Set<CdnDefaultConfigEntity> defaultConfig,
            CdnCustomConfigEntity config
    );

    String createUrl(
            String resourcePath,
            String appendedQuery
    );

    Map<String, String> removeSensitiveData(Map<String, String> queryMap);
}
