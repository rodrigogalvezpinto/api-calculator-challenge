package com.challenge.apicalculator.common.exception;

import com.challenge.apicalculator.domain.exception.PercentageServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PercentageServiceException.class)
    public ResponseEntity<ErrorResponse> handlePercentageServiceException(PercentageServiceException ex) {
        log.error("Error en el servicio de porcentaje: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
            new ErrorResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Error en el servicio de porcentaje",
                ex.getMessage(),
                LocalDateTime.now()
            ),
            HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        log.warn("Error de validación: {}", errors);
        
        return new ResponseEntity<>(
            new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de validación",
                errors.toString(),
                LocalDateTime.now()
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("Error de tipo en argumento: {}", ex.getMessage());
        return new ResponseEntity<>(
            new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de tipo en argumento",
                "El valor '" + ex.getValue() + "' no es válido para el parámetro " + ex.getName(),
                LocalDateTime.now()
            ),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(
            new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                "Ha ocurrido un error inesperado",
                LocalDateTime.now()
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
} 