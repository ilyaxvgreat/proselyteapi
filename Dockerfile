FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app
RUN echo deploying as:
RUN whoami

COPY . .
RUN mvn clean install -DskipTests=true

ENTRYPOINT ["java"]

CMD ["-jar", "/app/target/proselyteapi.jar"]