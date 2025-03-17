package com.challenge.apicalculator.controller;

import com.challenge.apicalculator.model.dto.CalculationRequest;
import com.challenge.apicalculator.model.dto.CalculationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Documentación de Swagger para CalculationController.
 * Esta clase contiene solo las anotaciones de documentación.
 */
@Tag(name = "Calculadora", description = "API para realizar cálculos con porcentaje dinámico")
public interface CalculationControllerDoc {

    @Operation(summary = "Realizar cálculo", description = "Suma dos números y aplica un porcentaje dinámico obtenido de un servicio externo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cálculo realizado correctamente",
                    content = @Content(schema = @Schema(implementation = CalculationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
            @ApiResponse(responseCode = "503", description = "Servicio externo no disponible")
    })
    @PostMapping("/calculate")
    ResponseEntity<CalculationResponse> calculate(
            @Valid @RequestBody CalculationRequest request,
            HttpServletRequest servletRequest);
} 