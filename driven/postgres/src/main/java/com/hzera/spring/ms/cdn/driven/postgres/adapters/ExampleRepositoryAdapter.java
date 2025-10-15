package com.hzera.spring.ms.cdn.driven.postgres.adapters;

import com.hzera.spring.ms.cdn.application.ports.driven.ExampleRepositoryPort;
import com.hzera.spring.ms.cdn.common.domain.HZeraPage;
import com.hzera.spring.ms.cdn.common.repository.builders.HZeraPageBuilder;
import com.hzera.spring.ms.cdn.domain.entity.ExampleEntity;
import com.hzera.spring.ms.cdn.driven.postgres.jpa.ExampleMOJpaRepository;
import com.hzera.spring.ms.cdn.driven.postgres.mappers.ExampleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleRepositoryAdapter implements ExampleRepositoryPort {
    private final HZeraPageBuilder HZeraPageBuilder;

    private final ExampleMOJpaRepository repository;

    private final ExampleMapper mapper;

    @Override
    public Optional<ExampleEntity> findById(Long id) {
        var exampleMO = repository.findById(id);

        return mapper.toOptionalEntity(exampleMO);
    }

    @Override
    public HZeraPage<ExampleEntity> findAll(Integer pageNumber, Integer pageSize, String sort) {
        var pageRequest = HZeraPageBuilder.builder().page(pageNumber).pageSize(pageSize).sort(sort).build();

        var resultPage = repository.findAll(pageRequest);

        return mapper.toEntities(resultPage);
    }

    @Override
    public ExampleEntity save(ExampleEntity entity) {
        var exampleModel = mapper.toModel(entity);

        var savedExample = repository.save(exampleModel);

        return mapper.toEntity(savedExample);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
