package com.challenge.apicalculator.adapters.inbound.api.controller;

import com.challenge.apicalculator.adapters.inbound.api.mapper.CalculationMapper;
import com.challenge.apicalculator.adapters.inbound.api.model.request.CalculationRequest;
import com.challenge.apicalculator.adapters.inbound.api.model.response.CalculationResponse;
import com.challenge.apicalculator.domain.service.CalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para realizar cálculos con porcentaje dinámico
 */
@RestController
@RequestMapping("/api/v1/calculations")
@Tag(name = "Cálculos", description = "API para realizar cálculos con porcentaje dinámico")
@RequiredArgsConstructor
@Slf4j
public class CalculationController {

    private final CalculationService calculationService;

    @Operation(summary = "Realizar cálculo con porcentaje dinámico", 
               description = "Suma dos números y aplica un porcentaje adicional obtenido de un servicio externo o caché")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "Cálculo realizado exitosamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = CalculationResponse.class))),
        @ApiResponse(responseCode = "400", 
                    description = "Datos de entrada inválidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", 
                    description = "Error interno del servidor",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })

    @PostMapping
    public ResponseEntity<CalculationResponse> calculate(
        @Parameter(description = "Datos para el cálculo", required = true)
        @Valid @RequestBody CalculationRequest request) {
        return ResponseEntity.ok(calculationService.calculate(request));
    }
} 