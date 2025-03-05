#! /bin/bash

docker rmi imgclientes
docker rmi imgcuentas
docker rmi imgreportes
docker rmi imgmysql
docker rmi mongo
docker rmi rabbitmq

cd mysql

docker build -t imgmysql .

cd ../msclientes

mvn clean install

docker build -t imgclientes .

cd ../mscuentas

mvn clean install

docker build -t imgcuentas .

cd ../msreportes

mvn clean install

docker build -t imgreportes .

docker network rm bancanet

docker network create bancanet