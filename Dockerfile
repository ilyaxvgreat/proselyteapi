FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app
RUN echo deploying as:
RUN whoami

COPY target/proselyteapi.jar .


ENTRYPOINT ["java"]

CMD ["-jar", "/app/proselyteapi.jar"]
