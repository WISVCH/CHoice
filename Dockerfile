FROM wisvch/spring-boot-base:1
COPY ./build/libs/choice.jar /srv/choice.jar
USER spring-boot
CMD ["/srv/choice.jar"]
