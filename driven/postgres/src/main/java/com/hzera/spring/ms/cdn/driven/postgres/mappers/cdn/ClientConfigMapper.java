package com.hzera.spring.ms.cdn.driven.postgres.mappers.cdn;

import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnClientConfigEntity;
import com.hzera.spring.ms.cdn.driven.postgres.models.cdn.CdnScopePathMO;
import com.hzera.spring.ms.cdn.driven.postgres.models.cdn.ClientConfigMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ClientConfigMapper {
    @Mapping(source = "model.scopePaths", target = "scopePaths")
    @Mapping(source = "admin", target = "isAdmin")
    @Mapping(source = "disabled", target = "isDisabled")
    @Mapping(source = "banned", target = "isBanned")
    CdnClientConfigEntity toEntity(ClientConfigMO model);

    default Optional<CdnClientConfigEntity> toOptionalEntity(Optional<ClientConfigMO> optionalModel) {
        return optionalModel.map(this::toEntity);
    }

    default String cdnScopePathMOToString(CdnScopePathMO scopePath) {
        if (scopePath == null) {
            return null;
        }

        return scopePath.getPath();
    }
}
