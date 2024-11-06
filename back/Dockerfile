FROM --platform=linux/amd64 openjdk:22
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} prestabanco-backend.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/prestabanco-backend.jar"]
