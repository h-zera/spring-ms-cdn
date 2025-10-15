package com.hzera.spring.ms.cdn.application.ports.driven;

import com.hzera.spring.ms.cdn.common.interfaces.SNACrudRepository;
import com.hzera.spring.ms.cdn.domain.entity.ExampleEntity;

public interface ExampleRepositoryPort extends SNACrudRepository<ExampleEntity, Long> {
}
