#Dockerfile mscuentas

FROM openjdk:17-ea-17-jdk-slim

LABEL maintainer "javaedi.et@gmail.com"

RUN adduser mscuentas

WORKDIR /home/mscuentas

COPY target/*.jar mscuentas.jar

RUN echo '#! /bin/bash \njava -Xms512m -Xmx1g -jar mscuentas.jar' >> entrypoint.sh && chmod +x entrypoint.sh

EXPOSE 8082

ENTRYPOINT ["/home/mscuentas/entrypoint.sh"]