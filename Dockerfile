FROM adoptopenjdk:14-hotspot AS builder
COPY . /src
WORKDIR /src
RUN ./gradlew build

FROM docker.pkg.github.com/wisvch/docker-spring-boot-base/2.2
COPY --from=builder /src/build/libs/choice.jar /srv/choice.jar
CMD ["/srv/choice.jar"]
