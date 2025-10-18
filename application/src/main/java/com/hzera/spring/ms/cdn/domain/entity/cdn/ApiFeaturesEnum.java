package com.hzera.spring.ms.cdn.domain.entity.cdn;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public enum ApiFeaturesEnum {
    FILE_SIGNING_ACCESS(Set.of()),
    FOLDER_SIGNING_ACCESS(Set.of()),
    ALL_CONFIGS(Set.of(ConfigTypeEnum.values())),
    EXPIRATION_CONFIG(Set.of(ConfigTypeEnum.EXPIRATION_TIME)),
    RED_RESTRICTION_CONFIG(Set.of(
            ConfigTypeEnum.ALLOWED_COUNTRIES,
            ConfigTypeEnum.BANNED_COUNTRIES,
            ConfigTypeEnum.REMOTE_IP
    ));

    private final Set<ConfigTypeEnum> configs;
}
