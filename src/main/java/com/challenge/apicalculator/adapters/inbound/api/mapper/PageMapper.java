package com.challenge.apicalculator.adapters.inbound.api.mapper;

import com.challenge.apicalculator.adapters.inbound.api.model.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Mapper para convertir entre Page de Spring y PageResponse
 */
@Component
public class PageMapper {

    /**
     * Convierte un Page de Spring a un PageResponse
     */
    public <T, R> PageResponse<R> toResponse(Page<T> page, Function<T, R> mapper) {
        List<R> content = page.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());

        return PageResponse.<R>builder()
                .content(content)
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
} 