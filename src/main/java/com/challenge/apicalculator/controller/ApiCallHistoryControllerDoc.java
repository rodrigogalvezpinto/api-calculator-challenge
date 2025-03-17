package com.challenge.apicalculator.controller;

import com.challenge.apicalculator.model.dto.ApiCallHistoryResponse;
import com.challenge.apicalculator.model.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

/**
 * Documentación de Swagger para ApiCallHistoryController.
 * Esta clase contiene solo las anotaciones de documentación.
 */
@Tag(name = "Historial", description = "API para consultar el historial de llamadas")
public interface ApiCallHistoryControllerDoc {

    @Operation(summary = "Obtener historial de llamadas", description = "Devuelve el historial de llamadas paginado")
    @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente",
            content = @Content(schema = @Schema(implementation = PageResponse.class)))
    @GetMapping
    ResponseEntity<PageResponse<ApiCallHistoryResponse>> getHistory(
            @Parameter(description = "Número de página (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Obtener historial por endpoint", description = "Devuelve el historial de llamadas filtrado por endpoint")
    @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente",
            content = @Content(schema = @Schema(implementation = PageResponse.class)))
    @GetMapping("/endpoint")
    ResponseEntity<PageResponse<ApiCallHistoryResponse>> getHistoryByEndpoint(
            @Parameter(description = "Endpoint a filtrar") @RequestParam String endpoint,
            @Parameter(description = "Número de página (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de página") @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Obtener historial por rango de fechas", description = "Devuelve el historial de llamadas filtrado por rango de fechas")
    @ApiResponse(responseCode = "200", description = "Historial obtenido correctamente",
            content = @Content(schema = @Schema(implementation = PageResponse.class)))
    @GetMapping("/date-range")
    ResponseEntity<PageResponse<ApiCallHistoryResponse>> getHistoryByDateRange(
            @Parameter(description = "Fecha de inicio (formato: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            
            @Parameter(description = "Fecha de fin (formato: yyyy-MM-dd'T'HH:mm:ss)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            
            @Parameter(description = "Número de página (0-indexed)")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "Tamaño de página")
            @RequestParam(defaultValue = "10") int size);
} 