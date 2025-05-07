package com.challenge.apicalculator.adapters.outbound.repository;

import com.challenge.apicalculator.adapters.outbound.repository.entity.ApiCallHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio JPA para el historial de llamadas a la API
 */
@Repository
public interface ApiCallHistoryJpaRepository extends JpaRepository<ApiCallHistoryEntity, Long> {

    /**
     * Busca el historial por endpoint
     */
    List<ApiCallHistoryEntity> findByEndpoint(String endpoint);

    /**
     * Busca el historial por rango de fechas
     */
    List<ApiCallHistoryEntity> findByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
} 