package com.challenge.apicalculator.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String PERCENTAGE_CACHE = "percentageCache";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(PERCENTAGE_CACHE);
    }

    // Limpiar el caché cada 30 minutos
    @Scheduled(fixedRateString = "${app.cache.percentage.ttl-minutes:30}000")
    public void evictPercentageCache() {
        cacheManager().getCache(PERCENTAGE_CACHE).clear();
    }
} 