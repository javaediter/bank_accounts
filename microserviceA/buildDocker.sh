#! /bin/bash
mvn clean install

docker build --no-cache -t backend-clientes .