package com.hzera.spring.ms.cdn.domain.entity.identity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    private Integer id;
    private String clientId;
    private String clientSecretHash;
}