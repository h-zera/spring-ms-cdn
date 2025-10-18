package com.hzera.spring.ms.cdn.domain.entity.cdn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class CdnResourceEntity implements Serializable {
    private String tokenHash;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresAt;
    private String path;
    private boolean isFolder;
    private String remoteIp;
    private String queryParams;
    private String requesterIp;
    private String mountedUrl;

    public String getFullQueryString() {
        return String.format("token=%s&%s", this.tokenHash, this.queryParams);
    }
}