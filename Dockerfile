FROM quay.io/wisvch/openjdk:11-jdk AS builder
COPY . /src
WORKDIR /src
RUN ./gradlew build

FROM quay.io/wisvch/spring-boot-base:2.1
COPY --from=builder /src/build/libs/choice.jar /srv/choice.jar
CMD ["/srv/choice.jar"]
