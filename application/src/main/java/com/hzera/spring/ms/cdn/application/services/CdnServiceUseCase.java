package com.hzera.spring.ms.cdn.application.services;

import com.hzera.spring.ms.cdn.application.exceptions.cdn.CdnClientResourceAccessDenied;
import com.hzera.spring.ms.cdn.application.ports.driven.cdn.CdnCachePort;
import com.hzera.spring.ms.cdn.application.ports.driven.cdn.CdnRepositoryPort;
import com.hzera.spring.ms.cdn.application.ports.driven.cdn.CdnSecureUrlProviderPort;
import com.hzera.spring.ms.cdn.application.ports.driver.CdnServicePort;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnClientConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnCustomConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnResourceEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CdnServiceUseCase implements CdnServicePort {
    private final CdnRepositoryPort cdnRepository;
    private final CdnSecureUrlProviderPort cdnSecureUrlProvider;
    private final CdnCachePort cdnCache;

    @Override
    public CdnResourceEntity getCdnResource(
            String clientId,
            String path,
            CdnCustomConfigEntity config
    ) {
        var cachedResource = cdnCache.get(clientId, path, config);
        if (cachedResource.isPresent()) {
            log.info("CDN Resource found in cache for clientId: {}, path: {}", clientId, path);
            return cachedResource.get();
        }

        var clientConfig = this.getValidClientConfig(clientId, path, config);

        var storedResource = cdnRepository.findValidResourceByPath(clientConfig.getId(), path);
        if (storedResource.isPresent()) {
            log.info("CDN Resource found in repository for clientId: {}, path: {}", clientId, path);
            var resource = storedResource.get();

            this.cacheRepositoryResource(clientId, path, resource, config);
            return resource;
        }

        var resource = this.createResource(path, clientConfig, config);

        return this.storeResource(clientConfig, path, resource, config);
    }

    private CdnClientConfigEntity getValidClientConfig(
            String clientId,
            String path,
            CdnCustomConfigEntity config
    ) {
        var clientConfig = cdnRepository.findByClientId(clientId)
                .orElseThrow(() -> new CdnClientResourceAccessDenied("Client not found or access denied."));

        if (!clientConfig.hasAccessTo(path, config)) {
            throw new CdnClientResourceAccessDenied("Client not found or access denied.");
        }

        return clientConfig;
    }

    private CdnResourceEntity createResource(
            String path,
            CdnClientConfigEntity clientConfig,
            CdnCustomConfigEntity customConfig
    ) {
        var queryMap = cdnSecureUrlProvider.createQueryMap(
                path,
                clientConfig.getDefaultConfigs(),
                customConfig
        );
        var secureMap = cdnSecureUrlProvider.removeSensitiveData(queryMap);
        var token = secureMap.get("token");
        secureMap.remove("token");

        var queryList = secureMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .toList();
        var stringQuery =  String.join("&", queryList);

        var url = cdnSecureUrlProvider.createUrl(path, String.format("token=%s&%s",token, stringQuery));

        var resourceBuilder = CdnResourceEntity.builder();

        resourceBuilder.tokenHash(token);
        var Inst = Instant.ofEpochSecond(Long.parseLong(queryMap.get("expires")));
        resourceBuilder.expiresAt(Inst.atOffset(ZoneOffset.UTC));
        resourceBuilder.path(path);
        resourceBuilder.isFolder(path.endsWith("/"));
        resourceBuilder.remoteIp(queryMap.get("remote_ip"));
        resourceBuilder.queryParams(stringQuery);
        //TODO requester ip
        resourceBuilder.mountedUrl(url);

        return resourceBuilder.build();
    }

    private void cacheRepositoryResource(String clientId, String path, CdnResourceEntity resource, CdnCustomConfigEntity config) {
        var currentTime = OffsetDateTime.now();
        var ttl = currentTime.until(resource.getExpiresAt(), ChronoUnit.SECONDS);
        cdnCache.save(clientId, path, resource, config, ttl);
    }

    private CdnResourceEntity storeResource(CdnClientConfigEntity client, String path, CdnResourceEntity resource, CdnCustomConfigEntity config) {
        var storedResource = cdnRepository.save(client, resource);

        this.cacheRepositoryResource(client.getClientId(), path, storedResource, config);

        return storedResource;
    }
}
