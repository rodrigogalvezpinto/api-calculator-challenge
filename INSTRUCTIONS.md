# Instrucciones de Ejecución

Este documento proporciona instrucciones detalladas para ejecutar y probar la API Calculator Challenge.

## Requisitos Previos

- **Docker Desktop**: Versión 4.0 o superior
- Para desarrollo local:
  - **Java 21**: JDK 21 o superior
  - **Maven 3.8+**: Para compilar el proyecto
  - **IDE**: IntelliJ IDEA, Eclipse o VS Code (recomendado)

## Ejecución con Docker Compose (Recomendado)

La forma más sencilla de ejecutar la aplicación es utilizando Docker Compose, que configurará automáticamente todos los servicios necesarios.

### Pasos:

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/rodrigogalvezpinto/api-calculator-challenge.git
   cd api-calculator-challenge
   ```

2. **Iniciar los servicios con Docker Compose**:
   ```bash
   docker compose up -d
   ```
   Este comando iniciará tres contenedores:
   - `api-calculator`: La aplicación Spring Boot
   - `calculator-db`: Base de datos PostgreSQL
   - `mock-percentage-service`: Servicio mock para el porcentaje

3. **Verificar que los contenedores estén en ejecución**:
   ```bash
   docker compose ps
   ```

4. **Acceder a la aplicación**:
   - API: [http://localhost:8080/api](http://localhost:8080/api)
   - Swagger UI: [http://localhost:8080/api/swagger-ui/index.html#/](http://localhost:8080/api/swagger-ui.html)

5. **Detener los servicios** (cuando hayas terminado):
   ```bash
   docker compose down
   ```
   
   Para eliminar también los volúmenes (datos de PostgreSQL):
   ```bash
   docker compose down -v
   ```

## Ejecución para Desarrollo Local

Si prefieres ejecutar la aplicación localmente para desarrollo:

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/rodrigogalvezpinto/api-calculator-challenge.git
   cd api-calculator-challenge
   ```

2. **Iniciar PostgreSQL con Docker** (opcional, puedes usar H2 para desarrollo):
   ```bash
   docker run -d --name calculator-db -p 5432:5432 -e POSTGRES_DB=calculator_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres:15-alpine
   ```

3. **Compilar la aplicación**:
   ```bash
   mvn clean package
   ```

4. **Ejecutar la aplicación**:
   ```bash
   java -jar target/api-calculator-0.0.1-SNAPSHOT.jar
   ```
   
   O desde Maven:
   ```bash
   mvn spring-boot:run
   ```

5. **Acceder a la aplicación**:
   - API: [http://localhost:8080/api](http://localhost:8080/api)
   - Swagger UI: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)

## Pruebas de la API

### Usando Swagger UI

La forma más sencilla de probar la API es utilizando Swagger UI:

1. Abre [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui.html) en tu navegador
2. Explora los endpoints disponibles
3. Prueba cada endpoint utilizando la interfaz interactiva

### Usando Postman

1. Importa la colección de Postman proporcionada en el archivo `postman_collection.json`
2. Ejecuta las solicitudes predefinidas

### Usando cURL

#### Cálculo con porcentaje dinámico:
```bash
curl -X POST "http://localhost:8080/api/calculator/calculate" \
     -H "Content-Type: application/json" \
     -d '{"num1": 10, "num2": 20}'
```

#### Obtener historial de llamadas:
```bash
curl -X GET "http://localhost:8080/api/history?page=0&size=10"
```

#### Filtrar historial por endpoint:
```bash
curl -X GET "http://localhost:8080/api/history/endpoint?endpoint=/calculator/calculate&page=0&size=10"
```

#### Filtrar historial por rango de fechas:
```bash
curl -X GET "http://localhost:8080/api/history/date-range?startDate=2023-01-01T00:00:00&endDate=2023-12-31T23:59:59&page=0&size=10"
```

## Ejecución de Pruebas Unitarias

Para ejecutar las pruebas unitarias:

```bash
mvn test
```

Las pruebas unitarias siguen el enfoque BDD (Behavior-Driven Development) utilizando AssertJ y Mockito, lo que mejora la legibilidad y mantenibilidad del código de prueba.

## Características Técnicas Destacadas

### Separación de la Documentación

La aplicación implementa una separación clara entre el código de los controladores y la documentación de Swagger/OpenAPI:

- Las interfaces `ApiCallHistoryControllerDoc` y `CalculationControllerDoc` contienen todas las anotaciones de documentación
- Los controladores implementan estas interfaces, manteniendo su código limpio y enfocado en la lógica de negocio

### Patrón Builder en DTOs

Los objetos de transferencia de datos (DTOs) utilizan el patrón Builder de Lombok para facilitar la creación de instancias, especialmente útil en los tests.

### Caché Configurable

El sistema de caché para el porcentaje está configurado para expirar cada 30 minutos, pero este valor es configurable a través de propiedades en `application.yml`.

## Solución de Problemas

### Error de conexión a PostgreSQL

Si encuentras errores de conexión a PostgreSQL, verifica:
- Que el contenedor de PostgreSQL esté en ejecución: `docker ps`
- Los logs del contenedor: `docker logs calculator-db`
- La configuración de conexión en `application.yml`

### Error con el servicio mock

Si el servicio mock no responde:
- Verifica que el contenedor esté en ejecución: `docker ps`
- Revisa los logs: `docker logs mock-percentage-service`
- Comprueba que el archivo de mapeo esté correctamente configurado en `mock/percentage-mapping.json`

### Otros problemas

Para cualquier otro problema, consulta los logs de la aplicación:
```bash
docker logs api-calculator
```

O si estás ejecutando localmente, revisa la consola donde se está ejecutando la aplicación. 