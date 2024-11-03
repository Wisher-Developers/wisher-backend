# Stage 1: Build the JAR file
FROM amazoncorretto:21 AS builder
WORKDIR /app
COPY api .
RUN chmod +x gradlew
RUN ./gradlew ktlintFormat
RUN ./gradlew build

# Stage 2: Create the final image
FROM amazoncorretto:21
VOLUME /tmp
COPY --from=builder /app/build/libs/api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
