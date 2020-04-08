FROM openjdk:11-jdk-slim as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw dependency:go-offline -B
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:11-jdk-slim


ARG DEPENDENCY=/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app


EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","-Ddefault.username=unisonuser", "-Ddefault.encoded=$2a$10$A2tDbzPnFa0ti2z5Da/HNeSlxVL2MC8fLK1AWXbphOI1Vo6S8swGC", "eu.acclimatize.unison.UnisonServerApplication"]
