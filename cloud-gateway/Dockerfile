FROM maven:3.9.6-eclipse-temurin-21 as build
COPY . .
RUN mvn clean package


FROM openjdk:21
EXPOSE 8083
COPY --from=build /target/cloud-gateway-0.0.1-SNAPSHOT.jar cloud-gateway.jar
ENTRYPOINT ["java","-jar","/cloud-gateway.jar"]
