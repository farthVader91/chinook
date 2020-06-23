FROM openjdk:8
ENV COMMIT_SHA $GITHUB_SHA
ENV COMMIT_REF $GITHUB_REF
COPY ./target/chinook-0.0.1-SNAPSHOT.jar /usr/src/myapp/artifact.jar
WORKDIR /usr/src/myapp
COPY ./docker/entrypoint.sh .
EXPOSE 8080
CMD ["bash", "entrypoint.sh"]