FROM openkbs/jdk11-mvn-py3 as build

WORKDIR /app

COPY pom.xml .
COPY src src

RUN (cd src/main/python; python switch_db_config.py)

RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jdk-slim

ARG DEPENDENCY=/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib	
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF	
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080

RUN apt-get update
RUN apt-get install -y gnupg wget
RUN wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | apt-key add -
RUN apt-get update
RUN apt-get install -y postgresql-client
COPY entrypoint.sh .
RUN chmod + entrypoint.sh

ENV POSTGRES_USER harmonie
ENV POSTGRES_PASSWORD harpasswd
ENV DB_HOST db

ENTRYPOINT ["./entrypoint.sh"]
