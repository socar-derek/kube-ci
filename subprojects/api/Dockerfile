FROM adoptopenjdk/openjdk8:alpine-jre
RUN mkdir /app
COPY build/libs/api-*.jar /app/api.jar
WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api.jar"]
