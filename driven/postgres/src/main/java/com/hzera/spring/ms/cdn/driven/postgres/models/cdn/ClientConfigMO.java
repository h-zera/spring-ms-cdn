package com.hzera.spring.ms.cdn.driven.postgres.models.cdn;

import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Parameter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_cdn_config")
public class ClientConfigMO {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @Type(
            value = EnumArrayType.class,
            parameters = @Parameter(
                name = AbstractArrayType.SQL_ARRAY_TYPE,
                value = "allowed_feature"
            )
    )
    @Column(name = "allowed_features", nullable = false, columnDefinition = "allowed_feature[]")
    private AllowedFeaturesEnum[] allowedFeatures;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin;

    @Column(name = "is_disabled", nullable = false)
    private boolean isDisabled;

    @Column(name = "is_banned", nullable = false)
    private boolean isBanned;

    @OneToMany(mappedBy = "clientConfig")
    private List<CdnScopePathMO> scopePaths;
}
