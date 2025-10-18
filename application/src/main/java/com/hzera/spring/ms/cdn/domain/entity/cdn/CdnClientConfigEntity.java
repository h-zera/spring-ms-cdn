package com.hzera.spring.ms.cdn.domain.entity.cdn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class CdnClientConfigEntity {
    private UUID id;
    private String clientId;
    private Set<ApiFeaturesEnum> allowedFeatures;
    private Set<String> scopePaths;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private boolean isAdmin;
    private boolean isDisabled;
    private boolean isBanned;

    private Set<CdnDefaultConfigEntity> defaultConfigs;

    public boolean hasAccessTo(String path, CdnCustomConfigEntity config) {
        if (isAdmin) {
            return true;
        }

        if (!isPathAllowed(path)) {
            return false;
        }

        return isConfigAllowed(config);
    }

    private boolean isPathAllowed(String path) {
        if (!allowedFeatures.contains(ApiFeaturesEnum.ALL_CONFIGS)) {
            if (path.endsWith("/") && !allowedFeatures.contains(ApiFeaturesEnum.FOLDER_SIGNING_ACCESS)) {
                return false;
            } else if (!path.endsWith("/") && !allowedFeatures.contains(ApiFeaturesEnum.FILE_SIGNING_ACCESS)) {
                return false;
            }
        }

        for (String scopePath : this.scopePaths) {
            if (path.startsWith(scopePath)) {
                return true;
            }
        }

        return false;
    }

    private boolean isConfigAllowed(CdnCustomConfigEntity config) {
        if (config == null || allowedFeatures.contains(ApiFeaturesEnum.ALL_CONFIGS)) return true;

        if (config.getExpiresIn() != null && !allowedFeatures.contains(ApiFeaturesEnum.EXPIRATION_CONFIG)) {
            return false;
        }

        if (config.getAllowedCountries() != null || config.getBlockedCountries() != null || config.getRemoteIp() != null) {
            return allowedFeatures.contains(ApiFeaturesEnum.RED_RESTRICTION_CONFIG);
        }

        return true;
    }
}
