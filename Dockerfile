FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace/app

COPY pom.xml .
COPY src src

RUN apk add maven && apk cache clean
RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENV GOOGLE_APPLICATION_CREDENTIALS=/app/keys/gcp/conta-servico-bdk-eventos.json

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "br.com.bdk.eventsmanager.EventsManagerApplication"]