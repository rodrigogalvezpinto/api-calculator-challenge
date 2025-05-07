package com.challenge.apicalculator.adapters.outbound.repository;

import com.challenge.apicalculator.adapters.outbound.repository.mapper.ApiCallHistoryEntityMapper;
import com.challenge.apicalculator.domain.model.ApiCallHistory;
import com.challenge.apicalculator.domain.repository.ApiCallHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de historial de llamadas a la API
 */
@Repository
@RequiredArgsConstructor
public class ApiCallHistoryRepositoryImpl implements ApiCallHistoryRepository {

    private final ApiCallHistoryJpaRepository jpaRepository;
    private final ApiCallHistoryEntityMapper mapper;

    @Override
    public ApiCallHistory save(ApiCallHistory apiCallHistory) {
        var entity = mapper.toEntity(apiCallHistory);
        entity = jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public List<ApiCallHistory> findAll() {
        return jpaRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<ApiCallHistory> findByEndpoint(String endpoint) {
        return jpaRepository.findByEndpoint(endpoint).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<ApiCallHistory> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaRepository.findByTimestampBetween(startDate, endDate).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }
} 