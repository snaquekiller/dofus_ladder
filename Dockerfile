FROM openjdk:8u151-jdk-alpine

RUN apk add --update curl && rm -rf /var/cache/apk/*

ENV TZ=Europe/Paris
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mvn clean install
RUN mkdir /app

ADD ./dofus-api/target/dofus-api.jar /app/dofus-api.jar



