FROM eclipse-temurin:24-jdk-alpine AS builder
WORKDIR /workspace

COPY . /workspace

RUN chmod +x ./gradlew && \
    ./gradlew --no-daemon clean bootJar

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
