FROM openjdk:21-jdk
LABEL authors="avamagi"

COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

#I LATER REALIZED THAT YOU CAN ONLY BUILD testcontainers with docker images - and you can not build docker images from dockerfiles inside a docker container