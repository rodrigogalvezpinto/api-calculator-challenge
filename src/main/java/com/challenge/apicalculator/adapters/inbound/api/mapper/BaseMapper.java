package com.challenge.apicalculator.adapters.inbound.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interfaz base para mappers
 */
public interface BaseMapper<D, E> {
    
    /**
     * Convierte una entidad a DTO
     */
    D toDto(E entity);

    /**
     * Convierte un DTO a entidad
     */
    E toEntity(D dto);

    /**
     * Convierte una lista de entidades a DTOs
     */
    default List<D> toDtoList(List<E> entities) {
        return entities.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de DTOs a entidades
     */
    default List<E> toEntityList(List<D> dtos) {
        return dtos.stream()
            .map(this::toEntity)
            .collect(Collectors.toList());
    }
} 