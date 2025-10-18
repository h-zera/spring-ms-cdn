package com.hzera.spring.ms.cdn.application.ports.driver;

import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnCustomConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnResourceEntity;

public interface CdnServicePort {
    CdnResourceEntity getCdnResource(
            String clientId,
            String path,
            CdnCustomConfigEntity config
    );
}
