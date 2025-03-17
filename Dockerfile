FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
# Descargar todas las dependencias
RUN mvn dependency:go-offline -B
COPY src ./src
# Empaquetar la aplicación
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar
# Puerto expuesto
EXPOSE 8080
# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"] 