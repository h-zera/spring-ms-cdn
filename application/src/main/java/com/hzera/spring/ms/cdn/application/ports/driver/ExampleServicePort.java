package com.hzera.spring.ms.cdn.application.ports.driver;

import com.hzera.spring.ms.cdn.application.exceptions.ExampleNotFoundException;
import com.hzera.spring.ms.cdn.common.domain.HZeraPage;
import com.hzera.spring.ms.cdn.domain.entity.ExampleEntity;

import java.util.Optional;

public interface ExampleServicePort {

    HZeraPage<ExampleEntity> getAllExamples(Integer pageNumber, Integer pageSize, String sort);

    Optional<ExampleEntity> getExample(Long id) throws ExampleNotFoundException;

    ExampleEntity createExample(ExampleEntity example);

    ExampleEntity updateExample(Long id, ExampleEntity exampleUpdate);

    void deleteExample(Long id);

}
