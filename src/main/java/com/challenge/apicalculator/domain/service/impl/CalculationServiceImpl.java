package com.challenge.apicalculator.domain.service.impl;

import com.challenge.apicalculator.adapters.inbound.api.mapper.CalculationMapper;
import com.challenge.apicalculator.adapters.inbound.api.model.request.CalculationRequest;
import com.challenge.apicalculator.adapters.inbound.api.model.response.CalculationResponse;
import com.challenge.apicalculator.domain.exception.ApiCalculatorException;
import com.challenge.apicalculator.domain.model.Calculation;
import com.challenge.apicalculator.domain.service.ApiCallHistoryService;
import com.challenge.apicalculator.domain.service.CalculationService;
import com.challenge.apicalculator.domain.service.PercentageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de cálculos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationServiceImpl implements CalculationService {

    private final CalculationMapper calculationMapper;
    private final ApiCallHistoryService apiCallHistoryService;
    private final PercentageService percentageService;
    private final ObjectMapper objectMapper;

    @Override
    public CalculationResponse calculate(CalculationRequest request) {
        String paramsJson = null;
        String responseJson = null;
        try {
            log.debug("Iniciando cálculo para request: {}", request);
            
            // Obtener el porcentaje dinámico
            double percentage = percentageService.getDynamicPercentage();
            log.debug("Porcentaje obtenido: {}", percentage);

            // Convertir request a entidad
            Calculation calculation = calculationMapper.toEntity(request);
            calculation.setPercentage(percentage);
            
            // Realizar cálculo
            Calculation result = performCalculation(calculation);
            log.debug("Cálculo realizado: {}", result);
            
            // Convertir resultado a DTO
            CalculationResponse response = calculationMapper.toDto(result);
            
            // Serializar parámetros y respuesta como JSON
            paramsJson = objectMapper.writeValueAsString(request);
            responseJson = objectMapper.writeValueAsString(response);
            
            // Registrar llamada exitosa
            apiCallHistoryService.logApiCall(
                "/api/v1/calculations",
                paramsJson,
                responseJson,
                null,
                true
            );
            
            return response;
        } catch (Exception e) {
            log.error("Error en el cálculo: {}", e.getMessage(), e);
            try {
                if (paramsJson == null) paramsJson = objectMapper.writeValueAsString(request);
            } catch (JsonProcessingException ignored) {}
            // Registrar llamada fallida
            apiCallHistoryService.logApiCall(
                "/api/v1/calculations",
                paramsJson != null ? paramsJson : request.toString(),
                null,
                e.getMessage(),
                false
            );
            throw new ApiCalculatorException(HttpStatus.INTERNAL_SERVER_ERROR, "CALCULATION_ERROR", "Error en el cálculo: " + e.getMessage());
        }
    }

    private Calculation performCalculation(Calculation calculation) {
        double sum = calculation.getNumber1() + calculation.getNumber2();
        double percentage = calculation.getPercentage();
        double finalResult = sum * (1 + percentage / 100);
        return Calculation.builder()
            .number1(calculation.getNumber1())
            .number2(calculation.getNumber2())
            .percentage(percentage)
            .result(finalResult)
            .build();
    }
} 