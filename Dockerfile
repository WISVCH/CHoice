FROM wisvch/spring-boot-base:2.1
COPY ./build/libs/choice.jar /srv/choice.jar
CMD ["/srv/choice.jar"]
