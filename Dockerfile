FROM openjdk:11
COPY build/libs/fx-rates-api*.jar fx-rates-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "fx-rates-api.jar"]
