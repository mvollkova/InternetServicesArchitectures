FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
ENV SERVER_PORT=8080 
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]