FROM maven:3.9.6-eclipse-temurin-21 as build
COPY . .
RUN mvn clean package


FROM openjdk:21
EXPOSE 8082
COPY --from=build /target/ms-books-payments-0.0.1-SNAPSHOT.jar ms-books-payments.jar
ENTRYPOINT ["java","-jar","/ms-books-payments.jar"]
