FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . .
RUN ./mvnw clean package
EXPOSE 8081
CMD ["java", "-jar", "target/my-java-app-1.0-SNAPSHOT.jar"]
