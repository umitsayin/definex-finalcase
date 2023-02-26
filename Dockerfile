FROM openjdk:11-jdk-slim

WORKDIR /app

COPY target/definex-myapp.jar /app

CMD ["java", "-jar", "definex-myapp.jar"]
