FROM amazoncorretto:21 AS builder
WORKDIR /app

COPY api .

RUN chmod +x gradlew && ./gradlew ktlintFormat

RUN ./gradlew clean build --info --no-daemon --parallel --build-cache --configure-on-demand

FROM amazoncorretto:21
VOLUME /tmp
COPY --from=builder /app/build/libs/api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
