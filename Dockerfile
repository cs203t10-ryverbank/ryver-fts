FROM openjdk:11-jre
WORKDIR /ryver-fts
COPY . /app
ENTRYPOINT ["java","-jar","/app/ryver-fts/target/fts-0.0.1-SNAPSHOT.jar"]