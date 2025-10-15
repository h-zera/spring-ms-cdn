package com.hzera.spring.ms.cdn.common.repository.builders;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HZeraPageBuilder {
    public Builder builder() {
        return new Builder();
    }

    private static Sort buildSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return Sort.unsorted();
        }

        String[] split = sort.split(",");
        List<Sort.Order> orders = new ArrayList<>();

        for (String s : split) {
            var order = buildOrder(s);

            if (order != null) {
                orders.add(order);
            }
        }

        return Sort.by(orders);
    }

    private static Sort.Order buildOrder(String sortParam) {
        if (sortParam == null || sortParam.isBlank()) {
            return null;
        }

        char directionChar = sortParam.toCharArray()[0];
        String param =  sortParam.substring(1);

        return new Sort.Order(directionChar == '-' ? Sort.Direction.DESC : Sort.Direction.ASC, param);
    }

    public static class Builder {
        private int page;
        private int pageSize;
        private String sort;

        public Builder page(int page) {
            this.page = Math.max(page - 1, 0);
            return this;
        }

        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder sort(String sort) {
            this.sort = sort;
            return this;
        }

        public PageRequest build() {
            if (this.sort == null || this.sort.isBlank()) {
                return PageRequest.of(page, pageSize);
            } else {
                Sort sortObject = buildSort(sort);

                return PageRequest.of(page, pageSize, sortObject);
            }
        }
    }
}
