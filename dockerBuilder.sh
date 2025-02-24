#! /bin/bash

docker rmi imgclientes
docker rmi imgcuentas
docker rmi imgmysql

cd mysql

docker build -t imgmysql .

cd ../msclientes

mvn clean install

docker build -t imgclientes .

cd ../mscuentas

mvn clean install

docker build -t imgcuentas .

docker network rm bancanet

docker network create bancanet