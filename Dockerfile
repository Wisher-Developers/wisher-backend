FROM amazoncorretto:21 AS builder
WORKDIR /app

RUN yum update -y && yum install -y dos2unix
COPY api .
RUN dos2unix gradlew
RUN chmod +x gradlew
RUN ./gradlew build

FROM amazoncorretto:21
VOLUME /tmp
COPY --from=builder /app/build/libs/api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar", "--debug"]
