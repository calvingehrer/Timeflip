FROM maven:3.5-jdk-8

RUN adduser --disabled-password nonroot

USER nonroot

RUN mkdir -p /user/nonroot

WORKDIR /user/nonroot/app

COPY ./pom.xml /user/nonroot/app/pom.xml

COPY ./src/main/resources/ecuador-theme-3.0.0.jar /user/nonroot/app/src/main/resources/ecuador-theme-3.0.0.jar

RUN mvn clean package

COPY ./src /user/nonroot/app/src

ENTRYPOINT [ "mvn", "spring-boot:run"]
