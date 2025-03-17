package com.challenge.apicalculator.controller;

import com.challenge.apicalculator.model.dto.CalculationRequest;
import com.challenge.apicalculator.model.dto.CalculationResponse;
import com.challenge.apicalculator.service.ApiCallHistoryService;
import com.challenge.apicalculator.service.CalculationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
@Slf4j
public class CalculationController implements CalculationControllerDoc {

    private final CalculationService calculationService;
    private final ApiCallHistoryService apiCallHistoryService;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<CalculationResponse> calculate(
            @Valid @RequestBody CalculationRequest request,
            HttpServletRequest servletRequest) {
        
        log.info("Recibida solicitud de cálculo: {}", request);
        
        try {
            // Realizar el cálculo
            CalculationResponse response = calculationService.calculate(request);
            
            // Registrar la llamada exitosa
            logApiCall(servletRequest, request, response, null, true);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Registrar la llamada fallida
            logApiCall(servletRequest, request, null, e.getMessage(), false);
            
            // Relanzar la excepción para que sea manejada por el GlobalExceptionHandler
            throw e;
        }
    }

    /**
     * Registra la llamada a la API de forma asíncrona.
     */
    private void logApiCall(HttpServletRequest request, Object requestBody, Object responseBody, 
                           String errorMessage, boolean successful) {
        try {
            String endpoint = request.getRequestURI();
            String parameters = objectMapper.writeValueAsString(requestBody);
            String response = responseBody != null ? objectMapper.writeValueAsString(responseBody) : null;
            
            apiCallHistoryService.logApiCall(endpoint, parameters, response, errorMessage, successful);
        } catch (JsonProcessingException e) {
            log.error("Error al serializar objetos para el registro de llamadas", e);
        }
    }
} 