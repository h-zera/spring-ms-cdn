package com.hzera.spring.ms.cdn.domain.entity.cdn;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CdnCustomConfigEntity {
    private Integer expiresIn;
    private List<String> allowedCountries;
    private List<String> blockedCountries;
    private String remoteIp;
}
