package com.hzera.spring.ms.cdn.common.interfaces;

import com.hzera.spring.ms.cdn.common.domain.HZeraPage;

import java.util.Optional;

public interface HZACrudRepository<T, I> {

    Optional<T> findById(I id);

    HZeraPage<T> findAll(Integer pageNumber, Integer pageSize, String sort);

    T save(T entity);

    void deleteById(I id);
}
