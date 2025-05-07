# API Calculator Challenge

API REST en Spring Boot para realizar cálculos con porcentaje dinámico y registro de historial de llamadas.

## Características

- Cálculo con porcentaje dinámico obtenido de un servicio externo
- Caché del porcentaje con TTL de 30 minutos
- Historial de llamadas asíncrono
- Documentación con Swagger
- Tests unitarios
- Despliegue con Docker y Docker Compose

## Requisitos

- **Git**: Para clonar el repositorio
- **Docker Desktop**: Versión 4.0 o superior
- **Docker Compose**: Incluido en Docker Desktop
- **Java 21** (solo si quieres ejecutar localmente)
- **Maven 3.8+** (solo si quieres compilar localmente)

## Clonación del Proyecto

```bash
git clone https://github.com/tu-usuario/api-calculator-challenge.git
cd api-calculator-challenge
```

## Estructura de Servicios (Docker)

Al levantar el proyecto con Docker Compose, se crearán **tres contenedores**:

- **api-calculator**: La API principal (Spring Boot)
- **calculator-db**: Base de datos PostgreSQL
- **mock-percentage-service**: Servicio externo mock (WireMock)

> **Nota:** El contenedor `api-calculator` puede requerir que lo inicies manualmente si los otros servicios aún no están listos. Espera a que `calculator-db` y `mock-percentage-service` estén en estado saludable y luego inicia `api-calculator` desde Docker Desktop o con:
> ```bash
> docker-compose start api-calculator
> ```

## Ejecución con Docker Compose (Recomendado)

1. **Construir y levantar los servicios:**
   ```bash
   docker-compose up --build
   ```
2. **Verificar el estado:**
   ```bash
   docker-compose ps
   ```
3. **Acceder a la API:**
   - API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/swagger-ui.html
4. **Detener los servicios:**
   ```bash
   docker-compose down
   ```

## Crear la Imagen Docker Manualmente

Si quieres construir la imagen de la API para subirla a Docker Hub:

```bash
docker build -t tu-usuario/api-calculator:latest .
```

Luego puedes subirla con:
```bash
docker push tu-usuario/api-calculator:latest
```

## Diferencias entre Mock Local y Mock Real

- **Mock Local:**
  - Endpoint: `http://localhost:8080/api/mock/percentage`
  - Solo existe si ejecutas la app en modo mock interno (útil para desarrollo local, sin Docker).
  - No es el mock real usado en integración o producción.

- **Mock Real (WireMock):**
  - Endpoint: `http://localhost:8081/api/mock/percentage`
  - Es un servicio externo real, levantado como contenedor Docker.
  - Es el que la API consume en ambientes Dockerizados y producción.

> **Importante:** En la imagen Docker, la API siempre apunta al mock real en el puerto 8081.

## Ejecución Local (Desarrollo)

1. **Compilar la aplicación:**
   ```bash
   mvn clean package
   ```
2. **Ejecutar la aplicación:**
   ```bash
   ./mvnw spring-boot:run
   # o
   java -jar target/api-calculator-0.0.1-SNAPSHOT.jar
   ```
3. **Acceder a la API:**
   - API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/swagger-ui.html

## Ejemplos de cURL

### Consultar el servicio mock externo (WireMock)
```bash
curl -X GET \
  'http://localhost:8081/api/mock/percentage' \
  -H 'accept: application/json'
```

### Cálculo con Porcentaje Dinámico
```bash
curl -X POST \
  'http://localhost:8080/api/v1/calculations' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{"number1": 10, "number2": 20}'
```

### Consultar historial de llamadas
```bash
curl -X GET 'http://localhost:8080/api/v1/history'
```

### Filtrar historial por endpoint
```bash
curl -X GET 'http://localhost:8080/api/v1/history/endpoint/{endpoint}'
```

### Filtrar historial por rango de fechas
```bash
curl -X GET 'http://localhost:8080/api/v1/history/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59'
```

## Notas y Buenas Prácticas

- Asegúrate de tener Docker Desktop corriendo antes de iniciar los servicios.
- Si algún contenedor no inicia automáticamente, revisa su estado en Docker Desktop y arráncalo manualmente si es necesario.
- El caché del porcentaje tiene un TTL de 30 minutos.
- La base de datos solo almacena el historial de llamadas.
- Puedes limpiar los volúmenes de Docker con:
  ```bash
  docker-compose down -v
  ```
- Para desarrollo local, puedes usar el mock interno, pero para pruebas reales usa siempre el mock externo (WireMock).

## Estructura del Proyecto

```
src/main/java/com/challenge/apicalculator/
├── adapters/
│   ├── inbound/
│   │   └── api/
│   │       ├── controller/
│   │       ├── mapper/
│   │       └── model/
│   └── outbound/
│       └── repository/
├── common/
│   ├── config/
│   └── exception/
└── domain/
    ├── model/
    └── service/
```

## Tests

Ejecutar los tests:
```bash
./mvnw test
```

## Configuración

### Base de Datos

- PostgreSQL en Docker
- Puerto: 5432
- Usuario: postgres
- Contraseña: postgres
- Base de datos: calculator

### Caché

- TTL: 30 minutos
- Tamaño máximo: 100 elementos

### Servicio Externo

- URL configurable en `application.yml`
- Modo mock disponible para pruebas

