package com.challenge.apicalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase principal de la aplicación
 */
@SpringBootApplication
@EnableAsync // Habilitar procesamiento asíncrono para el registro de historial
@EnableScheduling // Habilitar tareas programadas (por ejemplo, para limpiar caché)
public class ApiCalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiCalculatorApplication.class, args);
    }
} 