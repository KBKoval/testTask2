FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=task2.jar
COPY ${JAR_FILE} task.jar
RUN mkdir -p /usr/local/data
COPY goodreads_top100_from1980to2023_final.csv /usr/local/data
ENTRYPOINT ["java","-jar","/task.jar"]