FROM maven:3-jdk-8

RUN mkdir -p /home/nonroot/app

WORKDIR /home/nonroot/app

COPY pom.xml /home/nonroot/app/pom.xml
COPY ./src /home/nonroot/app/src
COPY ./src/main/resources/Docker_recources/application.properties /home/nonroot/app/src/main/resources/application.properties
COPY ./src/main/resources/Docker_recources/application.properties /home/nonroot/app/src/test/resources/application.properties

EXPOSE 8080

RUN mvn clean package -DskipTests

ENTRYPOINT [ "mvn", "spring-boot:run"]
