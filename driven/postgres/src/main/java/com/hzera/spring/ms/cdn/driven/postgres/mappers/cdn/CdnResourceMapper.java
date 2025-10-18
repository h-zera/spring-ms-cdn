package com.hzera.spring.ms.cdn.driven.postgres.mappers.cdn;

import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnResourceEntity;
import com.hzera.spring.ms.cdn.driven.postgres.models.cdn.CdnResourceMO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CdnResourceMapper {

    CdnResourceEntity toEntity(CdnResourceMO model);

    CdnResourceMO toModel(CdnResourceEntity entity);

    default Optional<CdnResourceEntity> toOptionalEntity(Optional<CdnResourceMO> optionalModel) {
        return optionalModel.map(this::toEntity);
    }
}
