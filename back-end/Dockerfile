FROM openkbs/jdk11-mvn-py3 as build

WORKDIR /app

COPY pom.xml .
COPY src src

RUN sudo chown -R $(whoami):$(whoami) .

RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jdk-slim

ARG DEPENDENCY=/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib	
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF	
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 8080

RUN apt-get update
RUN apt-get install -y gnupg curl
RUN curl -O https://www.postgresql.org/media/keys/ACCC4CF8.asc
RUN apt-key add ACCC4CF8.asc
RUN apt-get update
RUN apt-get install -y postgresql-client
COPY entrypoint.sh .
RUN chmod + entrypoint.sh

ENV POSTGRES_USER harmonie
ENV POSTGRES_PASSWORD harpasswd
ENV UNISON_DB_HOST db
ENV UNISON_USER unisonuser
#Dollar characters in a bcryt encoded password must be escaped for the UNISON_ENCDODE variable.
ENV UNISON_ENCODED \$2a\$10\$Id/K06.QPQACEzr8udi/m.DKlhChKCS7XFqfhjiWVH.TOvPrFBOom

ENTRYPOINT ["./entrypoint.sh"]
