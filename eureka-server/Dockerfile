FROM maven:3.9.6-eclipse-temurin-21 as build
COPY . .
RUN mvn clean package


FROM openjdk:21
EXPOSE 8761
COPY --from=build /target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar"]
