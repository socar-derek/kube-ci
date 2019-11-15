FROM adoptopenjdk/openjdk8:alpine-jre
RUN mkdir /app
COPY build/libs/grpc-*.jar /app/grpc.jar
WORKDIR /app

EXPOSE 6565

ENTRYPOINT ["java", "-jar", "grpc.jar"]
