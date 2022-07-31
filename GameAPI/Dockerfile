FROM maven:3.8.5-openjdk-18 as build

COPY ./ App/
WORKDIR App/

RUN ["mvn", "package", "-Dmaven.test.skip"]

WORKDIR target/

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "GameAPI-0.0.1.jar"]