FROM openjdk:8
ENV GITHUB_SHA n/a
ENV GITHUB_REF n/a
COPY ./target/chinook-0.0.1-SNAPSHOT.jar /usr/src/myapp/artifact.jar
WORKDIR /usr/src/myapp
COPY ./docker/entrypoint.sh .
EXPOSE 8080
CMD ["bash", "entrypoint.sh"]