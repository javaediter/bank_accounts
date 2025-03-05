#Dockerfile msreportes

FROM openjdk:17-ea-17-jdk-slim

LABEL maintainer "javaedi.et@gmail.com"

RUN adduser msreportes

WORKDIR /home/msreportes

COPY target/*.jar msreportes.jar

RUN echo '#! /bin/bash \njava -Xms512m -Xmx1g -jar msreportes.jar' >> entrypoint.sh && chmod +x entrypoint.sh

EXPOSE 8083

ENTRYPOINT ["/home/msreportes/entrypoint.sh"]