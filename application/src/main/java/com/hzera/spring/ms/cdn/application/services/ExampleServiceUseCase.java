package com.hzera.spring.ms.cdn.application.services;

import com.hzera.spring.ms.cdn.application.exceptions.ExampleNotFoundException;
import com.hzera.spring.ms.cdn.application.ports.driven.ExampleRepositoryPort;
import com.hzera.spring.ms.cdn.application.ports.driver.ExampleServicePort;
import com.hzera.spring.ms.cdn.common.domain.HZeraPage;
import com.hzera.spring.ms.cdn.domain.entity.ExampleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleServiceUseCase implements ExampleServicePort {
    private final ExampleRepositoryPort exampleRepository;
    private static final String ERROR_CODE = "C01";

    @Override
    public HZeraPage<ExampleEntity> getAllExamples(Integer pageNumber, Integer pageSize, String sort) {
        return exampleRepository.findAll(pageNumber, pageSize, sort);
    }

    @Override
    public Optional<ExampleEntity> getExample(Long id) throws ExampleNotFoundException {
        return exampleRepository.findById(id);
    }

    @Override
    public ExampleEntity createExample(ExampleEntity example) {
        return exampleRepository.save(example);
    }

    @Override
    public ExampleEntity updateExample(Long id, ExampleEntity exampleUpdate) {
        ExampleEntity example = getExample(id)
                .orElseThrow(() -> new ExampleNotFoundException(String.format("Example with id %s not found.", id), ERROR_CODE));

        example.setName(exampleUpdate.getName());
        example.setDescription(exampleUpdate.getDescription());
        example.setIdentification(exampleUpdate.getIdentification());
        example.setIdentificationType(exampleUpdate.getIdentificationType());

        return exampleRepository.save(example);
    }

    @Override
    public void deleteExample(Long id) {
        ExampleEntity example = getExample(id)
                .orElseThrow(() -> new ExampleNotFoundException(String.format("Example with id %s not found.", id), ERROR_CODE));

        exampleRepository.deleteById(example.getId());
    }
}
