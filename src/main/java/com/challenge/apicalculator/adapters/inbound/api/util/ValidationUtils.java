package com.challenge.apicalculator.adapters.inbound.api.util;

import com.challenge.apicalculator.domain.exception.ApiCalculatorException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Utilidades para validaciones
 */
public final class ValidationUtils {
    
    private ValidationUtils() {
        throw new IllegalStateException("Clase de utilidad");
    }

    /**
     * Valida que un objeto no sea nulo
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new ApiCalculatorException(HttpStatus.BAD_REQUEST, message);
        }
        return obj;
    }

    /**
     * Valida que una fecha de inicio sea anterior a una fecha de fin
     */
    public static void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new ApiCalculatorException(
                HttpStatus.BAD_REQUEST,
                "Las fechas de inicio y fin son requeridas"
            );
        }

        if (startDate.isAfter(endDate)) {
            throw new ApiCalculatorException(
                HttpStatus.BAD_REQUEST,
                "La fecha de inicio debe ser anterior a la fecha de fin"
            );
        }
    }

    /**
     * Valida que un número sea positivo
     */
    public static void validatePositiveNumber(double number, String fieldName) {
        if (number <= 0) {
            throw new ApiCalculatorException(
                HttpStatus.BAD_REQUEST,
                String.format("El campo %s debe ser un número positivo", fieldName)
            );
        }
    }

    /**
     * Valida que una cadena no esté vacía
     */
    public static void validateNotEmpty(String str, String fieldName) {
        if (str == null || str.trim().isEmpty()) {
            throw new ApiCalculatorException(
                HttpStatus.BAD_REQUEST,
                String.format("El campo %s no puede estar vacío", fieldName)
            );
        }
    }
} 