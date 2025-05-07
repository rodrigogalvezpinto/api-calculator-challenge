package com.challenge.apicalculator.domain.service.impl;

import com.challenge.apicalculator.domain.service.PercentageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "app.external-service.mock", havingValue = "true", matchIfMissing = true)
public class MockPercentageServiceImpl implements PercentageService {

    @Override
    @Cacheable(value = "percentage")
    public double getDynamicPercentage() {
        log.info("Usando servicio mock para el porcentaje externo");
        return 10.0;
    }
} 