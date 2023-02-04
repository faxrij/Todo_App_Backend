FROM openjdk:17-alpine
VOLUME /tmp
ADD /target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
#EXPOSE 8080
#ARG JAR_FILE=target/spring-boot-docker.jar
#ADD ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#FROM postgres:latest
#ADD . /var/lib/postgresql2/data