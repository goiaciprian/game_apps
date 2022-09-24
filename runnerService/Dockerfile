FROM maven:3.8.5-openjdk-17

COPY ./ App/
WORKDIR /App/

RUN ["mvn", "package", "-Dmaven.test.skip"]

WORKDIR /App/target/
ENTRYPOINT ["java", "-jar", "runnerService-0.0.1-SNAPSHOT.jar"]