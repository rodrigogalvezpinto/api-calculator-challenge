package com.challenge.apicalculator.common.swagger;

/**
 * Constantes para la API
 */
public final class ApiConstants {
    
    private ApiConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    // Códigos HTTP
    public static final String HTTP_CODE_200 = "200";
    public static final String HTTP_CODE_400 = "400";
    public static final String HTTP_CODE_404 = "404";
    public static final String HTTP_CODE_500 = "500";

    // Mensajes de estado HTTP
    public static final String HTTP_STATUS_200 = "OK";
    public static final String HTTP_STATUS_400 = "Bad Request";
    public static final String HTTP_STATUS_404 = "Not Found";
    public static final String HTTP_STATUS_500 = "Internal Server Error";

    // Mensajes de error
    public static final String ERROR_MESSAGE_NOT_FOUND = "Recurso no encontrado";
    public static final String ERROR_MESSAGE_BAD_REQUEST = "Solicitud inválida";
    public static final String ERROR_MESSAGE_INTERNAL_ERROR = "Error interno del servidor";

    // Valores por defecto
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";
    public static final String DEFAULT_SORT = "timestamp,desc";
    public static final String DEFAULT_ENDPOINT = "/api/v1/calculate";
    public static final String DEFAULT_START_DATE = "2024-01-01T00:00:00";
    public static final String DEFAULT_END_DATE = "2024-12-31T23:59:59";

    // Ejemplos de respuesta
    public static final String CALCULATION_RESPONSE = """
            {
                "result": 15.0,
                "number1": 10.0,
                "number2": 5.0,
                "percentage": 50.0
            }
            """;

    public static final String API_CALL_HISTORY_RESPONSE = """
            {
                "content": [
                    {
                        "id": 1,
                        "endpoint": "/api/v1/calculate",
                        "parameters": "{\\"number1\\": 10.0, \\"number2\\": 5.0}",
                        "response": "{\\"result\\": 15.0, \\"number1\\": 10.0, \\"number2\\": 5.0, \\"percentage\\": 50.0}",
                        "successful": true,
                        "errorMessage": null,
                        "timestamp": "2024-03-20T10:00:00"
                    }
                ],
                "pageable": {
                    "pageNumber": 0,
                    "pageSize": 10,
                    "sort": {
                        "sorted": true,
                        "unsorted": false,
                        "empty": false
                    }
                },
                "totalElements": 1,
                "totalPages": 1,
                "last": true,
                "size": 10,
                "number": 0,
                "first": true,
                "empty": false
            }
            """;
} 