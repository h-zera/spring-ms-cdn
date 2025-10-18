package com.hzera.spring.ms.cdn.common.interfaces;

import java.util.Optional;

public interface HZACacheRepository<T> {
    Optional<T> get(String key);

    void save(String key, T value, long ttlInSeconds);

    void delete(String key);
}
