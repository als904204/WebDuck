FROM openjdk:11-jre-slim
RUN mkdir /app
WORKDIR /app
COPY build/libs/*.jar /app/backend.jar
EXPOSE 8080


CMD ["java", "-Dspring.profiles.active=docker", "-jar", "/app/backend.jar"]
