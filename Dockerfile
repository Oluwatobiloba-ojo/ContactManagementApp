FROM maven:3.8.5-openjdk-21 AS build
COPY ../.. .
RUN mvn clean package -DskipTests

FROM openjdk:21.0.1-jdk-slim
COPY --from=build /target/ContactManager-1.0-SNAPSHOT.jar ContactManager.jar
EXPOSE 9065
ENTRYPOINT ["java", "-jar", "ContactManager.jar"]
