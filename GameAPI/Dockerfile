FROM maven:3.8.5-openjdk-17 as build

COPY ./ App/
WORKDIR /App/

RUN ["mvn", "package", "-Dmaven.test.skip"]

FROM maven:3.8.5-openjdk-17
WORKDIR /App
COPY --from=build /App/target/"GameAPI-0.0.1.jar" ./

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "GameAPI-0.0.1.jar"]