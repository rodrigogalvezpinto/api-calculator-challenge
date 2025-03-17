# API Calculator Challenge

## Descripción
API REST desarrollada en Spring Boot que implementa un servicio de cálculo con porcentaje dinámico y registro de historial de llamadas.

## Tecnologías y Herramientas

### Backend
- **Java 21**: Versión LTS más reciente de Java
- **Spring Boot 3.2.3**: Framework para desarrollo de aplicaciones Java
- **Spring Data JPA**: Para la capa de persistencia
- **Spring Cache**: Para implementación del sistema de caché
- **Spring Validation**: Para validación de datos de entrada
- **Lombok**: Para reducir código boilerplate

### Base de Datos
- **PostgreSQL 15**: Sistema de gestión de bases de datos relacional
- **Hibernate ORM**: Framework ORM para mapeo objeto-relacional

### Documentación
- **Springdoc OpenAPI 2.3.0**: Implementación de OpenAPI 3.0 para Spring Boot
- **Swagger UI**: Interfaz de usuario para visualizar y probar la API

### Testing
- **JUnit 5**: Framework para pruebas unitarias
- **Mockito**: Framework para mocks en pruebas
- **AssertJ**: Biblioteca para aserciones fluidas y BDD
- **Spring Boot Test**: Soporte para pruebas en Spring Boot

### Contenedorización
- **Docker**: Para empaquetar la aplicación y sus dependencias
- **Docker Compose**: Para orquestar múltiples contenedores

### Herramientas de Desarrollo
- **Maven**: Gestión de dependencias y construcción del proyecto
- **Git**: Control de versiones

## Arquitectura

La aplicación sigue una arquitectura en capas:

### Capa de Presentación
- **Controllers**: Manejan las solicitudes HTTP y devuelven respuestas apropiadas
- **DTOs**: Objetos de transferencia de datos para la comunicación con clientes
- **Documentación**: Interfaces separadas para la documentación de Swagger/OpenAPI

### Capa de Negocio
- **Services**: Contienen la lógica de negocio de la aplicación
- **Caché**: Implementación de caché para el porcentaje obtenido del servicio externo

### Capa de Persistencia
- **Repositories**: Interfaces para acceder a la base de datos
- **Entities**: Modelos de datos que se mapean a tablas en la base de datos

### Características Principales

#### 1. Cálculo con Porcentaje Dinámico
- Endpoint REST que recibe dos números, los suma y aplica un porcentaje
- El porcentaje se obtiene de un servicio externo (simulado con un servicio mock)

#### 2. Sistema de Caché
- Almacenamiento en memoria del porcentaje durante 30 minutos
- Fallback al último valor conocido si el servicio externo falla

#### 3. Registro Asíncrono de Historial
- Almacenamiento asíncrono de todas las llamadas a la API
- Consulta paginada del historial con filtros por endpoint y fecha

#### 4. Separación de Responsabilidades
- Documentación de API separada del código de los controladores
- Tests siguiendo el enfoque BDD (Behavior-Driven Development)
- Uso del patrón Builder para los DTOs

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── challenge/
│   │           └── apicalculator/
│   │               ├── config/           # Configuraciones (caché, RestTemplate)
│   │               ├── controller/       # Controladores REST y documentación
│   │               ├── exception/        # Manejo de excepciones
│   │               ├── model/            # Entidades y DTOs
│   │               ├── repository/       # Repositorios JPA
│   │               ├── service/          # Servicios de negocio
│   │               └── ApiCalculatorApplication.java
│   └── resources/
│       └── application.yml               # Configuración de la aplicación
├── test/
│   └── java/
│       └── com/
│           └── challenge/
│               └── apicalculator/
│                   ├── mocks/            # Clases mock para tests
│                   └── service/          # Tests de servicios
└── docker-compose.yml                    # Configuración de Docker Compose
```

## Documentación Adicional

Para instrucciones detalladas sobre cómo ejecutar y probar la aplicación, consulte el archivo [INSTRUCTIONS.md](INSTRUCTIONS.md).

Para probar la API usando Postman, importe la colección proporcionada en el archivo [postman_collection.json](postman_collection.json).
