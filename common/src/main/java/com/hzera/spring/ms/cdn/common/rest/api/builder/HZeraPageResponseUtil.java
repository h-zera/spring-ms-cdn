package com.hzera.spring.ms.cdn.common.rest.api.builder;

import com.hzera.spring.ms.cdn.common.domain.HZeraPage;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class HZeraPageResponseUtil {
    public <T> String buildNextPage(HZeraPage<T> page) {
        if (page == null || !page.hasNext()) return null;

        var next = page.nextPageable();

        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQueryParam("page", next.getPageNumber())
                .replaceQueryParam("size", next.getPageSize())
                .toUriString();
    }

    public <T> String buildPreviousPage(HZeraPage<T> page) {
        if (page == null || !page.hasPrevious()) return null;

        var previous = page.previousPageable();

        return ServletUriComponentsBuilder.fromCurrentRequest()
                .replaceQueryParam("page", previous.getPageNumber())
                .replaceQueryParam("size", previous.getPageSize())
                .toUriString();
    }
}
