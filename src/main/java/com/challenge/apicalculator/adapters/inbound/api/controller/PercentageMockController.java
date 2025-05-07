package com.challenge.apicalculator.adapters.inbound.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controlador mock para simular el servicio externo de porcentajes.
 *
 * <b>Nota:</b> Este endpoint <b>solo existe si corres la app en modo mock interno</b> (útil para desarrollo local).
 * Para pruebas de integración o ambientes productivos, se recomienda usar el mock externo (WireMock) levantando el servicio Docker correspondiente.
 *
 * Endpoint mock externo real: http://localhost:8081/api/mock/percentage
 */
@RestController
@RequestMapping("/api/mock/percentage")
@Tag(name = "Mock de Porcentaje (Interno)", 
     description = "Endpoint solo disponible en modo mock interno para pruebas locales. Para usar el mock real, levanta el servicio WireMock en Docker.")
@Slf4j
public class PercentageMockController {


    @Operation(summary = "Obtener porcentaje mock (solo modo local)", 
               description = "Retorna un porcentaje fijo del 10% para pruebas locales. Este endpoint solo está disponible si la app corre en modo mock interno. Para pruebas reales, usa el mock externo en http://localhost:8081/api/mock/percentage.")
    @ApiResponse(responseCode = "200", 
                description = "Porcentaje obtenido exitosamente",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = Map.class)))

    @GetMapping
    public ResponseEntity<Map<String, Double>> getPercentage() {
        log.debug("Retornando porcentaje mock: 10.0");
        return ResponseEntity.ok(Map.of("percentage", 10.0));
    }
} 