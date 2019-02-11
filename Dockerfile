FROM openjdk:11-jre-slim

ENV TZ=Europe/Paris
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir /app

ADD ./target/dofus-api.jar /app/dofus-api.jar

#HEALTHCHECK CMD curl --fail http://localhost:8080 || exit 1

