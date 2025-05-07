package com.challenge.apicalculator.domain.service.impl;

import com.challenge.apicalculator.adapters.inbound.api.mapper.ApiCallHistoryMapper;
import com.challenge.apicalculator.adapters.inbound.api.model.response.ApiCallHistoryResponse;
import com.challenge.apicalculator.domain.model.ApiCallHistory;
import com.challenge.apicalculator.domain.repository.ApiCallHistoryRepository;
import com.challenge.apicalculator.domain.service.ApiCallHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de historial de llamadas
 */
@Service
@RequiredArgsConstructor
public class ApiCallHistoryServiceImpl implements ApiCallHistoryService {

    private final ApiCallHistoryRepository apiCallHistoryRepository;
    private final ApiCallHistoryMapper apiCallHistoryMapper;

    @Override
    @Async
    public void logApiCall(String endpoint, String parameters, String response, String errorMessage, boolean successful) {
        ApiCallHistory history = ApiCallHistory.builder()
            .endpoint(endpoint)
            .parameters(parameters)
            .response(response)
            .errorMessage(errorMessage)
            .successful(successful)
            .timestamp(LocalDateTime.now())
            .build();

        apiCallHistoryRepository.save(history);
    }

    @Override
    public List<ApiCallHistoryResponse> getApiCallHistory() {
        return apiCallHistoryRepository.findAll().stream()
            .map(apiCallHistoryMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<ApiCallHistoryResponse> getApiCallHistoryByEndpoint(String endpoint) {
        if (endpoint == null || endpoint.trim().isEmpty()) {
            throw new IllegalArgumentException("El endpoint no puede estar vacío");
        }
        return apiCallHistoryRepository.findByEndpoint(endpoint).stream()
            .map(apiCallHistoryMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<ApiCallHistoryResponse> getApiCallHistoryByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
        return apiCallHistoryRepository.findByDateRange(startDate, endDate).stream()
            .map(apiCallHistoryMapper::toDto)
            .collect(Collectors.toList());
    }
} 