package com.hzera.spring.ms.cdn.driven.postgres.mappers.client;

import com.hzera.spring.ms.cdn.domain.entity.identity.ClientEntity;
import com.hzera.spring.ms.cdn.driven.postgres.models.client.ClientMO;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientEntity toEntity(ClientMO model);

    ClientMO toModel(ClientEntity entity);

    default Optional<ClientEntity> toOptionalEntity(Optional<ClientMO> optionalModel) {
        return optionalModel.map(this::toEntity);
    }
}
