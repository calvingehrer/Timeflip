FROM java:8
FROM maven:alpine

# image layer
WORKDIR /src
ADD pom.xml /src
RUN mvn verify clean --fail-never

# Image layer: with the application
COPY . /src
ADD ./src/main/resources/Docker_recources/application.properties /src/src/main/resources
ADD ./src/main/resources/Docker_recources/application.properties /src/src/test/resources
RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8080
ENTRYPOINT ["mvn","spring-boot:run"]







#FROM maven:3.5-jdk-8

#RUN adduser --disabled-password nonroot


#RUN mkdir -p /home/nonroot

#WORKDIR /home/nonroot/app

#COPY ./pom.xml /home/nonroot/app/pom.xml

#COPY ./src/main/resources/ecuador-theme-3.0.0.jar /home/nonroot/app/src/main/resources/ecuador-theme-3.0.0.jar

#RUN mvn clean package

#COPY ./src /home/nonroot/app/src

#ENTRYPOINT [ "mvn", "spring-boot:run"]
