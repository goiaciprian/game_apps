FROM maven:3.8.5-openjdk-18

COPY ./ App/
WORKDIR App/

RUN ["mvn", "package", "-Dmaven.test.skip"]

WORKDIR target/
ENTRYPOINT ["java", "-jar", "runnerService-0.0.1-SNAPSHOT.jar"]