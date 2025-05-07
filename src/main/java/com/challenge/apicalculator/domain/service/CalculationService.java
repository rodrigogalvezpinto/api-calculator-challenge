package com.challenge.apicalculator.domain.service;

import com.challenge.apicalculator.adapters.inbound.api.model.request.CalculationRequest;
import com.challenge.apicalculator.adapters.inbound.api.model.response.CalculationResponse;

/**
 * Interfaz del servicio para cálculos
 */
public interface CalculationService {
    
    /**
     * Realiza un cálculo y registra la llamada
     */
    CalculationResponse calculate(CalculationRequest request);
} 