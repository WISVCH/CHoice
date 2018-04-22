FROM wisvch/spring-boot-base:1
COPY ./build/libs/choice.jar /srv/choice.jar
CMD ["/srv/choice.jar"]
