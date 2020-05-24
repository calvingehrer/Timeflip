FROM maven:3.5-jdk-8

RUN adduser --disabled-password nonroot


RUN mkdir -p /home/nonroot

WORKDIR /home/nonroot/app

COPY ./pom.xml /home/nonroot/app/pom.xml

COPY ./src/main/resources/ecuador-theme-3.0.0.jar /home/nonroot/app/src/main/resources/ecuador-theme-3.0.0.jar

RUN mvn clean package

COPY ./src /home/nonroot/app/src

ENTRYPOINT [ "mvn", "spring-boot:run"]
