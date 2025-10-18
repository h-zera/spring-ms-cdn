package com.hzera.spring.ms.cdn.driver.api.rest.v1.mappers;

import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnCustomConfigEntity;
import com.hzera.spring.ms.cdn.domain.entity.cdn.CdnResourceEntity;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.SigningConfigResource;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.SigningResource;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.SigningResourceQueryParamsInner;
import com.hzera.spring.ms.cdn.driver.api.rest.v1.openapi.cdn.sign.model.SigningResourceResponse;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CdnDTOMapper {
    public abstract CdnCustomConfigEntity toConfigEntity(SigningConfigResource resource);

    public SigningResource toSigningResource(CdnResourceEntity entity) {
        return SigningResource.builder()
                .urlPathToken(entity.getMountedUrl())
                .queryString(entity.getFullQueryString())
                .queryParams(mapQueryParams(entity.getFullQueryString()))
                .build();
    }

    private List<SigningResourceQueryParamsInner> mapQueryParams(String queryParams) {
        var params = queryParams.split("&");

        var queryParamsList = new ArrayList<SigningResourceQueryParamsInner>();

        for (String param : params) {
            String[] keyValue = param.split("=", 2);
            if (keyValue.length == 2) {
                var paramBuilder = SigningResourceQueryParamsInner.builder()
                        .param(keyValue[0])
                        .value(keyValue[1]);

                if (keyValue[0].equals("expires")) {
                    OffsetDateTime offsetDateTime = Instant.ofEpochSecond(Integer.parseInt(keyValue[1]))
                            .atOffset(ZoneOffset.UTC);
                    paramBuilder.alternateValue(offsetDateTime.toString());
                }

                if (keyValue[0].equals("token_path")) {
                    paramBuilder.alternateValue(keyValue[1].replaceAll("%2F", "/"));
                }

                queryParamsList.add(paramBuilder.build());
            }
        }
        return queryParamsList;
    }

    public SigningResourceResponse toSigningResourceResponse(CdnResourceEntity resource) {
        return SigningResourceResponse.builder()
                .data(toSigningResource(resource))
                .build();
    }
}
