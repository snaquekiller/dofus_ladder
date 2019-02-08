FROM openjdk:11-jre-slim

ENV TZ=Europe/Paris
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Install maven
RUN apt-get update
RUN apt-get install -y maven


# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml

# Adding source, compile and package into a fat jar
ADD dofus-api /code/
ADD dofus-data /code/
ADD dofus-service /code/

RUN cd /code

RUN ls -al *
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]
RUN ["mvn", "package"]

RUN mkdir /app

ADD /code/dofus-api/target/dofus-api.jar /app/dofus-api.jar
RUN rm -rf /code



