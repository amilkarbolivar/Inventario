# syntax=docker/dockerfile:1
FROM gradle:8.10-jdk21-alpine AS build
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon -x test

FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/inventario
ENV SPRING_DATASOURCE_USERNAME=inventario
ENV SPRING_DATASOURCE_PASSWORD=inventario
ENTRYPOINT ["java", "-jar", "app.jar"]