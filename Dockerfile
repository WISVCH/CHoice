FROM wisvch/alpine-java:8_server-jre_unlimited
ADD build/libs/choice.jar /srv/choice.jar
WORKDIR /srv
EXPOSE 8080
CMD "/srv/choice.jar"
