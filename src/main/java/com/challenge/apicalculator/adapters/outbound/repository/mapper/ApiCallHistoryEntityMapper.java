package com.challenge.apicalculator.adapters.outbound.repository.mapper;

import com.challenge.apicalculator.adapters.outbound.repository.entity.ApiCallHistoryEntity;
import com.challenge.apicalculator.domain.model.ApiCallHistory;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidades de dominio y entidades de persistencia de ApiCallHistory
 */
@Component
public class ApiCallHistoryEntityMapper {

    /**
     * Convierte una entidad de dominio a una entidad de persistencia
     */
    public ApiCallHistoryEntity toEntity(ApiCallHistory apiCallHistory) {
        return ApiCallHistoryEntity.builder()
                .id(apiCallHistory.getId())
                .endpoint(apiCallHistory.getEndpoint())
                .parameters(apiCallHistory.getParameters())
                .response(apiCallHistory.getResponse())
                .successful(apiCallHistory.isSuccessful())
                .errorMessage(apiCallHistory.getErrorMessage())
                .timestamp(apiCallHistory.getTimestamp())
                .build();
    }

    /**
     * Convierte una entidad de persistencia a una entidad de dominio
     */
    public ApiCallHistory toDomain(ApiCallHistoryEntity entity) {
        return ApiCallHistory.builder()
                .id(entity.getId())
                .endpoint(entity.getEndpoint())
                .parameters(entity.getParameters())
                .response(entity.getResponse())
                .successful(entity.isSuccessful())
                .errorMessage(entity.getErrorMessage())
                .timestamp(entity.getTimestamp())
                .build();
    }
} 