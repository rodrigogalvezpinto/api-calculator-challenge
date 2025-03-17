package com.challenge.apicalculator.controller;

import com.challenge.apicalculator.model.dto.ApiCallHistoryResponse;
import com.challenge.apicalculator.model.dto.PageResponse;
import com.challenge.apicalculator.service.ApiCallHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Slf4j
public class ApiCallHistoryController implements ApiCallHistoryControllerDoc {

    private final ApiCallHistoryService apiCallHistoryService;

    @Override
    public ResponseEntity<PageResponse<ApiCallHistoryResponse>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Obteniendo historial de llamadas - página: {}, tamaño: {}", page, size);
        PageResponse<ApiCallHistoryResponse> response = apiCallHistoryService.getApiCallHistory(page, size);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PageResponse<ApiCallHistoryResponse>> getHistoryByEndpoint(
            @RequestParam String endpoint,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Obteniendo historial de llamadas por endpoint: {} - página: {}, tamaño: {}", endpoint, page, size);
        PageResponse<ApiCallHistoryResponse> response = apiCallHistoryService.getApiCallHistoryByEndpoint(endpoint, page, size);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PageResponse<ApiCallHistoryResponse>> getHistoryByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("Obteniendo historial de llamadas por rango de fechas: {} a {} - página: {}, tamaño: {}",
                startDate, endDate, page, size);
        
        PageResponse<ApiCallHistoryResponse> response = apiCallHistoryService.getApiCallHistoryByDateRange(
                startDate, endDate, page, size);
        
        return ResponseEntity.ok(response);
    }
} 