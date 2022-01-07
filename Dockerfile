FROM adoptopenjdk:11-hotspot AS builder

ADD https://ch.tudelft.nl/certs/wisvch.crt /usr/local/share/ca-certificates/wisvch.crt
RUN chmod 0644 /usr/local/share/ca-certificates/wisvch.crt && \
    update-ca-certificates && \
    keytool -noprompt -keystore $JAVA_HOME/lib/security/cacerts -storepass changeit -importcert -alias wisvch -file /usr/local/share/ca-certificates/wisvch.crt

COPY . /src
WORKDIR /src
RUN ./gradlew build

FROM wisvch/spring-boot-base:2.1
COPY --from builder /src/build/libs/choice.jar /srv/choice.jar
CMD ["/srv/choice.jar"]
