FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/project-tracking-api-0.0.1-SNAPSHOT.jar project-tracking-api.jar
ENTRYPOINT ["java","-jar","/project-tracking-api.jar"]