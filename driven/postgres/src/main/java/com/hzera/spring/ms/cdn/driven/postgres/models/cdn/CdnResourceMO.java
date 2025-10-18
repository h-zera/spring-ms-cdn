package com.hzera.spring.ms.cdn.driven.postgres.models.cdn;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CDN_RESOURCE")
public class CdnResourceMO {
    @Id
    @Column(name = "token_hash", nullable = false)
    private String tokenHash;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "is_folder", nullable = false)
    private boolean isFolder;

    @Column(name = "remote_ip")
    private String remoteIp;

    @Column(name = "query_params", nullable = false)
    private String queryParams;

    @Column(name = "requester_ip")
    private String requesterIp;

    @Column(name = "mounted_url", nullable = false)
    private String mountedUrl;

    @ManyToOne
    @JoinColumn(name = "config_id", nullable = false)
    private ClientConfigMO clientConfig;
}
