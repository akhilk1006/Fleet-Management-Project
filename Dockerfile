FROM openjdk:8-jdk-alpine
COPY ./trucker-api/target/trucker-api-0.0.1-SNAPSHOT.jar api.jar
COPY ./docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod 755 /docker-entrypoint.sh
ENTRYPOINT ["./docker-entrypoint.sh"]
CMD ["java","-jar", "-Dspring.profiles.active=prod", "api.jar"]