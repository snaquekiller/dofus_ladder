FROM openjdk:11-jre-slim

ENV TZ=Europe/Paris
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Install maven
RUN apt-get update
RUN apt-get install -y maven


# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml

# Adding source, compile and package into a fat jar
ADD dofus-api /code/dofus-api
ADD dofus-data /code/dofus-data
ADD dofus-service /code/dofus-service

WORKDIR /code

RUN ["mvn", "package"]

WORKDIR /

RUN mkdir /app

RUN mv /code/dofus-api/target/dofus-api.jar /app/
RUN rm -rf /code



