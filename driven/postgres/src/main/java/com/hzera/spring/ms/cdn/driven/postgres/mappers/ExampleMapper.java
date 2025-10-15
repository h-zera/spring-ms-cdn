package com.hzera.spring.ms.cdn.driven.postgres.mappers;

import com.hzera.spring.ms.cdn.common.domain.HZeraPage;
import com.hzera.spring.ms.cdn.domain.entity.ExampleEntity;
import com.hzera.spring.ms.cdn.driven.postgres.models.ExampleMO;
import com.hzera.spring.ms.cdn.driven.postgres.models.IdentificationTypeMOEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ExampleMapper {
    ExampleEntity toEntity(ExampleMO model);

    //@Mapping(target = "identificationType", resultType = IdentificationTypeMOEnum.class)
    ExampleMO toModel(ExampleEntity entity);

    default HZeraPage<ExampleEntity> toEntities(Page<ExampleMO> modelsPage) {
        var page = modelsPage.map(this::toEntity);

        return HZeraPage.of(page);
    }

    default Optional<ExampleEntity> toOptionalEntity(Optional<ExampleMO> optionalModel) {
        return optionalModel.map(this::toEntity);
    }

    List<ExampleEntity> listModelToEntity(List<ExampleMO> list);
}