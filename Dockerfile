FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} app.jar

COPY ./init-scripts/init.sql.sh /docker-entrypoint-initdb.d/initdb.sh

RUN chmod +x /docker-entrypoint-initdb.d/initdb.sh

CMD ["java","-jar","/app.jar"]