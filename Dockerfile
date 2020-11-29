FROM adoptopenjdk:11-hotspot AS builder
COPY . /src
WORKDIR /src
RUN ./gradlew build

FROM wisvch/spring-boot-base:2.1
COPY --from=builder /src/build/libs/choice.jar /srv/choice.jar
CMD ["/srv/choice.jar"]
