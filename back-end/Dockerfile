FROM openjdk:11-jdk-slim
EXPOSE 8080
ARG JAR_FILE=target/unison-server-0.1.1-SNAPSHOT.jar
ADD ${JAR_FILE} unison-server.jar
ENTRYPOINT ["java","-jar","/unison-server.jar"]
