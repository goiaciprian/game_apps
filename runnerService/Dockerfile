FROM maven:3.8.5-openjdk-17 as build

COPY ./ App/
WORKDIR /App/

RUN ["mvn", "package", "-Dmaven.test.skip"]

WORKDIR /
RUN ["mvn","archetype:generate","-DgroupId=testing","-DartifactId=testingProject","-DarchetypeArtifactId=maven-archetype-quickstart","-DinteractiveMode=false", "-DartifactVersion=17"]

FROM maven:3.8.5-openjdk-17

WORKDIR /App/
COPY --from=build /App/target/"runnerService-0.0.1-SNAPSHOT.jar" ./
COPY --from=build /testingProject ./testingProject
COPY --from=build /App/testingProjectPom.xml /App/testingProject/pom.xml

ENV TESTPROJECTPATH=/App/testingProject

WORKDIR /App/testingProject
RUN ["mvn", "install", "-Dmaven.test.skip"]

WORKDIR /App/
ENTRYPOINT ["java", "-jar", "runnerService-0.0.1-SNAPSHOT.jar"]