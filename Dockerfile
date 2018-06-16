FROM openjdk:8-jdk-alpine
COPY ./target/trucker-api-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT ["java","-jar", "-Dspring.profiles.active=prod", "api.jar"]