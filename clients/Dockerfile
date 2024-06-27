FROM ubuntu:20.04

LABEL maintainer = "javaedi.et@gmail.com"
LABEL description = "microservicio de gestion de clientes"

RUN apt update && \
    apt install -y locales && \
	apt install -y apt && \
	apt install -y openjdk-17-jdk && \
	apt clean && \
	rm -rf /var/lib/apt/lists/*
	
ENV DB_PORT=${DB_PORT} DB_HOSTNAME=${DB_HOSTNAME} DB_USER=${DB_USER} DB_PASSWORD=${DB_PASSWORD}

EXPOSE 8081
	
WORKDIR /usr/src/

COPY target/*.jar ./app.jar
	
CMD ["java", "-jar", "app.jar"]