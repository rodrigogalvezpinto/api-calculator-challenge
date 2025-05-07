package com.challenge.apicalculator.adapters.inbound.api.controller;

import com.challenge.apicalculator.adapters.inbound.api.model.response.ApiCallHistoryResponse;
import com.challenge.apicalculator.domain.service.ApiCallHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para gestionar el historial de llamadas a la API
 */
@RestController
@RequestMapping("/api/v1/history")
@Tag(name = "Historial de Llamadas", description = "API para consultar el historial de llamadas a la API")
@RequiredArgsConstructor
@Slf4j
public class CallHistoryController {

    private final ApiCallHistoryService apiCallHistoryService;

    @Operation(summary = "Obtener historial completo", 
               description = "Retorna el historial completo de llamadas a la API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Historial obtenido exitosamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ApiCallHistoryResponse.class))),
        @ApiResponse(responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })

    @GetMapping
    public ResponseEntity<List<ApiCallHistoryResponse>> getHistory() {
        return ResponseEntity.ok(apiCallHistoryService.getApiCallHistory());
    }

    @Operation(summary = "Obtener historial por endpoint", 
               description = "Retorna el historial de llamadas filtrado por un endpoint específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Historial obtenido exitosamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ApiCallHistoryResponse.class))),
        @ApiResponse(responseCode = "400", 
                    description = "Endpoint inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })

    @GetMapping("/endpoint/{endpoint}")
    public ResponseEntity<List<ApiCallHistoryResponse>> getHistoryByEndpoint(
        @Parameter(description = "Endpoint a filtrar", required = true)
        @PathVariable String endpoint) {
        return ResponseEntity.ok(apiCallHistoryService.getApiCallHistoryByEndpoint(endpoint));
    }


    @Operation(summary = "Obtener historial por rango de fechas", 
               description = "Retorna el historial de llamadas dentro de un rango de fechas específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Historial obtenido exitosamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ApiCallHistoryResponse.class))),
        @ApiResponse(responseCode = "400", 
                    description = "Rango de fechas inválido",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })

    @GetMapping("/date-range")
    public ResponseEntity<List<ApiCallHistoryResponse>> getHistoryByDateRange(
        @Parameter(description = "Fecha de inicio (formato: yyyy-MM-dd'T'HH:mm:ss)", required = true)
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @Parameter(description = "Fecha de fin (formato: yyyy-MM-dd'T'HH:mm:ss)", required = true)
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(apiCallHistoryService.getApiCallHistoryByDateRange(startDate, endDate));
    }
} 