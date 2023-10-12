FROM eclipse-temurin:17 AS builder
COPY . /src
WORKDIR /src
RUN ./gradlew build

FROM ghcr.io/wisvch/spring-boot-base:2.5.5
COPY --from=builder /src/build/libs/choice.jar /srv/choice.jar
CMD ["/srv/choice.jar"]
