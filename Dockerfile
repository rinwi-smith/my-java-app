FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/my-java-app-1.0-SNAPSHOT.jar .
RUN apt-get update && apt-get install -y maven
EXPOSE 8081
CMD ["java", "-jar", "my-java-app-1.0-SNAPSHOT.jar"]