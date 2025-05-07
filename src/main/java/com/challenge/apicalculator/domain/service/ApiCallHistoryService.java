package com.challenge.apicalculator.domain.service;

import com.challenge.apicalculator.adapters.inbound.api.model.response.ApiCallHistoryResponse;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para gestionar el historial de llamadas a la API
 */
public interface ApiCallHistoryService {
    
    /**
     * Registra una llamada en el historial de forma asíncrona
     *
     * @param endpoint Endpoint llamado
     * @param parameters Parámetros de la llamada
     * @param response Respuesta generada
     * @param errorMessage Error si ocurrió
     * @param successful Indica si la llamada fue exitosa
     */
    void logApiCall(String endpoint, String parameters, String response, String errorMessage, boolean successful);

    /**
     * Obtiene el historial completo de llamadas
     *
     * @return Lista de llamadas registradas
     */
    List<ApiCallHistoryResponse> getApiCallHistory();

    /**
     * Obtiene el historial de llamadas filtrado por endpoint
     *
     * @param endpoint Endpoint a filtrar
     * @return Lista de llamadas al endpoint especificado
     */
    List<ApiCallHistoryResponse> getApiCallHistoryByEndpoint(String endpoint);

    /**
     * Obtiene el historial de llamadas filtrado por rango de fechas
     *
     * @param startDate Fecha de inicio
     * @param endDate Fecha de fin
     * @return Lista de llamadas en el rango de fechas
     */
    List<ApiCallHistoryResponse> getApiCallHistoryByDateRange(LocalDateTime startDate, LocalDateTime endDate);
} 