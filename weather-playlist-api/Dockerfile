FROM frolvlad/alpine-oraclejdk8:slim
LABEL maintainer="albert.jsouza@outlook.com"
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/weather-playlist-api-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
