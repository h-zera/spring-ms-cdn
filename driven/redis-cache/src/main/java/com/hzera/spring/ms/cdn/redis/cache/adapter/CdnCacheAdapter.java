package com.hzera.spring.ms.cdn.redis.cache.adapter;

import com.hzera.spring.ms.cdn.application.ports.driven.cdn.CdnCachePort;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnCustomConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnResourceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CdnCacheAdapter implements CdnCachePort {
    private final RedisTemplate<String, CdnResourceEntity> redisTemplate;

    @Override
    public String buildKey(String clientId, String path, CdnCustomConfigEntity config) {
        return clientId + buildConfigValues(config) + ":/" + path;
    }

    private String buildConfigValues(CdnCustomConfigEntity config) {
        if (config == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        if (config.getExpiresIn() != null) {
            sb.append(";");
            sb.append("expiresIn=").append(config.getExpiresIn());
        }
        if (config.getAllowedCountries() != null) {
            sb.append(";");
            var countries = String.join(",", config.getAllowedCountries());
            sb.append("allowedCountries=").append(countries);
        }
        if (config.getBlockedCountries() != null) {
            sb.append(";");
            var countries = String.join(",", config.getBlockedCountries());
            sb.append("bannedCountries=").append(countries);
        }
        if (config.getRemoteIp() != null) {
            sb.append(";");
            sb.append("remoteIp=").append(config.getRemoteIp());
        }

        return sb.toString();
    }

    @Override
    public Optional<CdnResourceEntity> get(String key) {
        var value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(value);
    }

    @Override
    public void save(String key, CdnResourceEntity value, long ttlInSeconds) {
        redisTemplate.opsForValue().set(key, value, ttlInSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
