FROM openjdk:8-jdk-alpine
MAINTAINER SeWas

COPY /build/libs/pitching-service-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Xmx200m","-jar","/app.jar"]

EXPOSE 4001