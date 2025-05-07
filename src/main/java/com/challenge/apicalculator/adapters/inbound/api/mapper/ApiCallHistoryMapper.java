package com.challenge.apicalculator.adapters.inbound.api.mapper;

import com.challenge.apicalculator.adapters.inbound.api.model.response.ApiCallHistoryResponse;
import com.challenge.apicalculator.domain.model.ApiCallHistory;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre DTOs y entidades de historial de llamadas
 */
@Component
public class ApiCallHistoryMapper implements BaseMapper<ApiCallHistoryResponse, ApiCallHistory> {

    @Override
    public ApiCallHistoryResponse toDto(ApiCallHistory entity) {
        if (entity == null) {
            return null;
        }

        return ApiCallHistoryResponse.builder()
            .id(entity.getId())
            .endpoint(entity.getEndpoint())
            .parameters(entity.getParameters())
            .response(entity.getResponse())
            .successful(entity.isSuccessful())
            .errorMessage(entity.getErrorMessage())
            .timestamp(entity.getTimestamp())
            .build();
    }

    @Override
    public ApiCallHistory toEntity(ApiCallHistoryResponse dto) {
        if (dto == null) {
            return null;
        }

        return ApiCallHistory.builder()
            .id(dto.getId())
            .endpoint(dto.getEndpoint())
            .parameters(dto.getParameters())
            .response(dto.getResponse())
            .successful(dto.isSuccessful())
            .errorMessage(dto.getErrorMessage())
            .timestamp(dto.getTimestamp())
            .build();
    }
} 