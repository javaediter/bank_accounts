#Dockerfile msclientes

FROM openjdk:17-ea-17-jdk-slim

LABEL maintainer "javaedi.et@gmail.com"

RUN adduser msclientes

WORKDIR /home/msclientes

COPY target/*.jar msclientes.jar

RUN echo '#! /bin/bash \njava -Xms512m -Xmx1g -jar msclientes.jar' >> entrypoint.sh && chmod +x entrypoint.sh

EXPOSE 8081

ENTRYPOINT ["/home/msclientes/entrypoint.sh"]