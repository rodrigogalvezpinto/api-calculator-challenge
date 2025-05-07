package com.challenge.apicalculator.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Configuración de caché para el servicio de porcentajes
 * 
 * Características:
 * - TTL configurable (por defecto 30 minutos)
 * - Tamaño máximo de 100 elementos
 * - Limpieza automática de elementos expirados
 * - Estadísticas habilitadas para monitoreo
 */
@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

    @Value("${app.cache.percentage.ttl-minutes:30}")
    private int ttlMinutes;

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
            .expireAfterWrite(ttlMinutes, TimeUnit.MINUTES)
            .maximumSize(100)
            .recordStats();
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("percentage");
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
} 