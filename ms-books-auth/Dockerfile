FROM maven:3.9.6-eclipse-temurin-21 as build
COPY . .
RUN mvn clean package


FROM openjdk:21
EXPOSE 8084
COPY --from=build /target/ms-books-auth-0.0.1-SNAPSHOT.jar ms-books-auth.jar
ENTRYPOINT ["java","-jar","/ms-books-auth.jar"]
