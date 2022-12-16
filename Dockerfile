FROM openjdk:17-alpine
ADD "./target/ParaCasa-0.0.1-SNAPSHOT.jar" "ParaCasa-0.0.1-SNAPSHOT.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","ParaCasa-0.0.1-SNAPSHOT.jar"]