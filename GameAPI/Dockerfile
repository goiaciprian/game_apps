FROM maven:3.8.5-openjdk-17 as build

COPY ./ App/
WORKDIR /App/

RUN ["mvn", "package", "-Dmaven.test.skip"]

WORKDIR /
RUN ["mvn","archetype:generate","-DgroupId=testing","-DartifactId=testingProject","-DarchetypeArtifactId=maven-archetype-quickstart","-DinteractiveMode=false", "-DartifactVersion=17"]

FROM maven:3.8.5-openjdk-17
WORKDIR /App
COPY --from=build /App/target/"GameAPI-0.0.1.jar" ./
COPY --from=build /testingProject ./testingProject
COPY ./testingProjectPom.xml /App/testingProject/pom.xml

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "GameAPI-0.0.1.jar"]