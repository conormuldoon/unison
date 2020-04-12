FROM openkbs/jdk11-mvn-py3 as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN (cd src/main/python; python switch_db_config.py)

RUN ./mvnw dependency:go-offline -B
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jdk-slim


ARG DEPENDENCIES=/app/target/dependency

COPY --from=build ${DEPENDENCIES}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCIES}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCIES}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","-Ddefault.username=unisonuser", "-Ddefault.encoded=$2a$10$A2tDbzPnFa0ti2z5Da/HNeSlxVL2MC8fLK1AWXbphOI1Vo6S8swGC","-D" "eu.acclimatize.unison.UnisonServerApplication"]
