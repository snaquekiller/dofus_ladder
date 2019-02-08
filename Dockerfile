FROM openjdk:11-jre-slim

RUN apk add --update curl && rm -rf /var/cache/apk/*



ENV TZ=Europe/Paris
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# Install maven
RUN apt-get update
RUN apt-get install -y maven


# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml

# Adding source, compile and package into a fat jar
ADD src /code/src
RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]
RUN ["mvn", "package"]

RUN mkdir /app

ADD /code/dofus-api/target/dofus-api.jar /app/dofus-api.jar
RUN rm -rf /code



