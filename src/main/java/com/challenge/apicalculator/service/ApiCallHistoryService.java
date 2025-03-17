package com.challenge.apicalculator.service;

import com.challenge.apicalculator.model.ApiCallHistory;
import com.challenge.apicalculator.model.dto.ApiCallHistoryResponse;
import com.challenge.apicalculator.model.dto.PageResponse;
import com.challenge.apicalculator.repository.ApiCallHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiCallHistoryService {

    private final ApiCallHistoryRepository apiCallHistoryRepository;

    /**
     * Registra una llamada a la API de forma asíncrona.
     *
     * @param endpoint El endpoint llamado
     * @param parameters Los parámetros de la llamada
     * @param response La respuesta de la llamada
     * @param errorMessage El mensaje de error (si lo hay)
     * @param successful Si la llamada fue exitosa
     */
    @Async
    public void logApiCall(String endpoint, String parameters, String response, String errorMessage, boolean successful) {
        try {
            ApiCallHistory history = ApiCallHistory.builder()
                    .timestamp(LocalDateTime.now())
                    .endpoint(endpoint)
                    .parameters(parameters)
                    .response(response)
                    .errorMessage(errorMessage)
                    .successful(successful)
                    .build();
            
            apiCallHistoryRepository.save(history);
            log.info("Registro de llamada a la API guardado: {}", history);
        } catch (Exception e) {
            log.error("Error al guardar el registro de llamada a la API", e);
        }
    }

    /**
     * Obtiene el historial de llamadas a la API paginado.
     *
     * @param page El número de página (0-indexed)
     * @param size El tamaño de la página
     * @return El historial de llamadas paginado
     */
    public PageResponse<ApiCallHistoryResponse> getApiCallHistory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ApiCallHistory> historyPage = apiCallHistoryRepository.findAllByOrderByTimestampDesc(pageable);
        
        return createPageResponse(historyPage);
    }

    /**
     * Obtiene el historial de llamadas a la API filtrado por endpoint y paginado.
     *
     * @param endpoint El endpoint a filtrar
     * @param page El número de página (0-indexed)
     * @param size El tamaño de la página
     * @return El historial de llamadas filtrado y paginado
     */
    public PageResponse<ApiCallHistoryResponse> getApiCallHistoryByEndpoint(String endpoint, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ApiCallHistory> historyPage = apiCallHistoryRepository.findByEndpointContainingOrderByTimestampDesc(endpoint, pageable);
        
        return createPageResponse(historyPage);
    }

    /**
     * Obtiene el historial de llamadas a la API filtrado por fecha y paginado.
     *
     * @param startDate La fecha de inicio
     * @param endDate La fecha de fin
     * @param page El número de página (0-indexed)
     * @param size El tamaño de la página
     * @return El historial de llamadas filtrado y paginado
     */
    public PageResponse<ApiCallHistoryResponse> getApiCallHistoryByDateRange(
            LocalDateTime startDate, LocalDateTime endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ApiCallHistory> historyPage = apiCallHistoryRepository.findByTimestampBetweenOrderByTimestampDesc(
                startDate, endDate, pageable);
        
        return createPageResponse(historyPage);
    }

    /**
     * Convierte una página de ApiCallHistory a PageResponse<ApiCallHistoryResponse>.
     *
     * @param historyPage La página de ApiCallHistory
     * @return La respuesta paginada
     */
    private PageResponse<ApiCallHistoryResponse> createPageResponse(Page<ApiCallHistory> historyPage) {
        List<ApiCallHistoryResponse> content = historyPage.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        
        return PageResponse.<ApiCallHistoryResponse>builder()
                .content(content)
                .pageNumber(historyPage.getNumber())
                .pageSize(historyPage.getSize())
                .totalElements(historyPage.getTotalElements())
                .totalPages(historyPage.getTotalPages())
                .last(historyPage.isLast())
                .build();
    }

    /**
     * Mapea un ApiCallHistory a ApiCallHistoryResponse.
     *
     * @param history El ApiCallHistory
     * @return El ApiCallHistoryResponse
     */
    private ApiCallHistoryResponse mapToResponse(ApiCallHistory history) {
        return ApiCallHistoryResponse.builder()
                .id(history.getId())
                .timestamp(history.getTimestamp())
                .endpoint(history.getEndpoint())
                .parameters(history.getParameters())
                .response(history.getResponse())
                .errorMessage(history.getErrorMessage())
                .successful(history.isSuccessful())
                .build();
    }
} 