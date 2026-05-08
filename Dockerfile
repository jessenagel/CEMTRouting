FROM amazoncorretto:17-alpine-jdk
LABEL maintainer=jessenagel.nl
COPY target/cemtrouting-0.1.0.jar app.jar
COPY bevaarbaarheid.json bevaarbaarheid.json
EXPOSE 7575
ENTRYPOINT ["java", "-jar", "/app.jar"]
