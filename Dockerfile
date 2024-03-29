FROM maven:3-openjdk-17-slim AS build

COPY . /app
WORKDIR /app

RUN mvn install -P jar -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:17-jdk-slim

RUN apt-get update -y && apt-get install -y libturbojpeg-dev

COPY --from=build /app/target/dependency/BOOT-INF/classes /app
COPY --from=build /app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=build /app/target/dependency/META-INF /app/META-INF

EXPOSE 8080

ENTRYPOINT ["java", "-cp", "/app:/app/lib/*", "-Djava.library.path=/usr/lib/x86_64-linux-gnu", "org.iish.hsn.invoer.Application"]
