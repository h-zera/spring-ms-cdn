package com.hzera.spring.ms.cdn.driven.postgres.models.cdn;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CDN_SCOPE_PATH")
public class CdnScopePathMO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "is_folder", nullable = false)
    private boolean isFolder;

    @Column(name = "subpaths_count", nullable = false)
    private int subpathCount;

    @ManyToOne
    @JoinColumn(name = "config_id", nullable = false)
    private ClientConfigMO clientConfig;
}
