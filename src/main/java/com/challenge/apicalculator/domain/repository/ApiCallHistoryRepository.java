package com.challenge.apicalculator.domain.repository;

import com.challenge.apicalculator.domain.model.ApiCallHistory;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfaz del repositorio para el historial de llamadas
 */
public interface ApiCallHistoryRepository {
    
    /**
     * Guarda un registro en el historial
     */
    ApiCallHistory save(ApiCallHistory apiCallHistory);

    /**
     * Obtiene todo el historial
     */
    List<ApiCallHistory> findAll();

    /**
     * Obtiene el historial filtrado por endpoint
     */
    List<ApiCallHistory> findByEndpoint(String endpoint);

    /**
     * Obtiene el historial filtrado por rango de fechas
     */
    List<ApiCallHistory> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
} 