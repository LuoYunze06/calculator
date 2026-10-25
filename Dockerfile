FROM maven:3.8.8-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /app/target/calculator-1.0.jar calculator.jar
ENTRYPOINT ["java","-jar","calculator.jar"]