#!/bin/bash

# Crear imagen de db
docker build -t emisa-mariadb .

# Ejecutar db
docker run --detach --network=emisa-network --name emisa-mariadb \
  --env MARIADB_ROOT_PASSWORD=password \
  --env MARIADB_USER=admin \
  --env MARIADB_PASSWORD=password \
  --env MARIADB_DATABASE=emisa \
  -p 3306:3306 \
  emisa-mariadb
