package com.hzera.spring.ms.cdn.driven.postgres.jpa;

import com.hzera.spring.ms.cdn.driven.postgres.models.ExampleMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleMOJpaRepository extends JpaRepository<ExampleMO, Long> {
}
