package com.hzera.spring.ms.cdn.common.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class HZeraPage<T> extends PageImpl<T> {

    public HZeraPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public static <T> HZeraPage<T> of(Page<T> page) {
        return new HZeraPage<>(page.getContent(), page.getPageable(), page.getTotalElements());
    }

}
