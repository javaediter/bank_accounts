#! /bin/bash

docker network rm bancanet

docker network create bancanet

docker build --no-cache -t mysql-banca .