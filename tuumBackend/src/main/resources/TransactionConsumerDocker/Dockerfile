FROM openjdk:21-jdk
LABEL authors="avamagi"

COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]