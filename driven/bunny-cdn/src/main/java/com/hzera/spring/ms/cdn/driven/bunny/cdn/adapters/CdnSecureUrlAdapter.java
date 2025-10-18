package com.hzera.spring.ms.cdn.driven.bunny.cdn.adapters;

import com.hzera.spring.ms.cdn.application.ports.driven.cdn.CdnSecureUrlProviderPort;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnCustomConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnDefaultConfigEntity;
import com.hzera.spring.ms.cdn.driven.bunny.cdn.providers.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CdnSecureUrlAdapter implements CdnSecureUrlProviderPort {
    @Value("${CDN_SECRET_KEY}")
    private String cdnToken;

    @Value("${CDN_URL}")
    private String cdnUrl;

    private final TokenGenerator tokenGenerator;

    @Override
    public Map<String, String> createQueryMap(
            String resourcePath,
            Set<CdnDefaultConfigEntity> defaultConfig,
            CdnCustomConfigEntity config
    ) {
        Map<String, String> queryMap = new HashMap<>();

        StringBuilder hashableBuilder = new StringBuilder();
        hashableBuilder.append(cdnToken);
        hashableBuilder.append(resourcePath);

        long expiresAt = getExpiresAt(config, defaultConfig);
        queryMap.put("expires", String.valueOf(expiresAt));
        hashableBuilder.append(expiresAt);

        String remoteIp = getRemoteIp(config, defaultConfig);
        if (remoteIp != null) {
            queryMap.put("remote_ip", remoteIp);
            hashableBuilder.append(remoteIp);
        }

        String allowedCountries = getCountries(config, defaultConfig, CdnDefaultConfigEntity.Param.ALLOWED_COUNTRIES);
        if (allowedCountries != null) {
            queryMap.put("token_countries", allowedCountries);
            hashableBuilder.append("token_countries=").append(allowedCountries);
        }

        String blockedCountries = getCountries(config, defaultConfig, CdnDefaultConfigEntity.Param.BLOCKED_COUNTRIES);
        if (blockedCountries != null) {
            queryMap.put("token_countries_blocked", blockedCountries);
            if (allowedCountries != null) hashableBuilder.append("&");
            hashableBuilder.append("token_countries_blocked=").append(blockedCountries);
        }

        if (resourcePath.endsWith("/")) {
            queryMap.put("token_path", resourcePath.replaceAll("/", "%2F"));
            if (allowedCountries != null || blockedCountries != null) hashableBuilder.append("&");
            hashableBuilder.append("token_path=").append(resourcePath);
        }

        var token = tokenGenerator.generateToken(hashableBuilder.toString());
        var resultMap = new HashMap<String, String>();
        resultMap.put("token", token);
        resultMap.putAll(queryMap);

        return resultMap;
    }

    @Override
    public String createUrl(String resourcePath, String appendedQuery) {
        final boolean isFolder = resourcePath.endsWith("/");

        StringBuilder url = new StringBuilder();
        url.append(cdnUrl);

        if (isFolder) {
            url.append(String.format("/bcdn_%s", appendedQuery));
            url.append(resourcePath);
        } else {
            url.append(resourcePath);
            url.append(String.format("?%s", appendedQuery));
        }

        return url.toString();
    }

    @Override
    public Map<String, String> removeSensitiveData(Map<String, String> queryMap) {
        var newQueryMap = new java.util.HashMap<>(queryMap);
        newQueryMap.remove("remote_ip");
        newQueryMap.remove("token_countries");
        newQueryMap.remove("token_countries_blocked");
        return newQueryMap;
    }

    private long getExpiresAt(CdnCustomConfigEntity config, Set<CdnDefaultConfigEntity> defaultConfig) {
        var now = Instant.now().getEpochSecond();

        if (config != null && config.getExpiresIn() != null) {
            return now + config.getExpiresIn();
        } else if (defaultConfig == null) {
            return now + 3600;
        }

        for (CdnDefaultConfigEntity defaultConfigEntity : defaultConfig) {
            if (CdnDefaultConfigEntity.Param.fromParam(defaultConfigEntity.getParam()).isPresent() &&
                    CdnDefaultConfigEntity.Param.fromParam(defaultConfigEntity.getParam()).get() ==
                            CdnDefaultConfigEntity.Param.EXPIRES) {
                return now + Integer.parseInt(defaultConfigEntity.getParam());
            }
        }

        return now + 3600; // Default to 1 hour
    }

    private String getRemoteIp(CdnCustomConfigEntity config, Set<CdnDefaultConfigEntity> defaultConfig) {
        if (config != null && config.getRemoteIp() != null) {
            return config.getRemoteIp();
        } else if (defaultConfig == null) {
            return null;
        }

        for (CdnDefaultConfigEntity defaultConfigEntity : defaultConfig) {
            if (CdnDefaultConfigEntity.Param.fromParam(defaultConfigEntity.getParam()).isPresent() &&
                    CdnDefaultConfigEntity.Param.fromParam(defaultConfigEntity.getParam()).get() ==
                            CdnDefaultConfigEntity.Param.REMOTE_IP) {
                return defaultConfigEntity.getValue();
            }
        }

        return null;
    }

    private String getCountries(CdnCustomConfigEntity config, Set<CdnDefaultConfigEntity> defaultConfig, CdnDefaultConfigEntity.Param type) {
        if (config != null) {
            if (type == CdnDefaultConfigEntity.Param.ALLOWED_COUNTRIES && config.getAllowedCountries() != null && !config.getAllowedCountries().isEmpty()) {
                return String.join(",", config.getAllowedCountries());
            }
            if (type == CdnDefaultConfigEntity.Param.ALLOWED_COUNTRIES && config.getBlockedCountries() != null && !config.getBlockedCountries().isEmpty()) {
                return String.join(",", config.getBlockedCountries());
            }
        }

        if (defaultConfig == null) {
            return null;
        }

        for (CdnDefaultConfigEntity defaultConfigEntity : defaultConfig) {
            if (CdnDefaultConfigEntity.Param.fromParam(defaultConfigEntity.getParam()).isPresent()) {
                var param = CdnDefaultConfigEntity.Param.fromParam(defaultConfigEntity.getParam()).get();
                if (param == type) {
                    return defaultConfigEntity.getValue();
                }
            }
        }

        return null;
    }
}
