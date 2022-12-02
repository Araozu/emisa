# Crear imagen del proyecto
docker build -t emisa .

# Crear red virtual
docker network create emisa-network

# Ejecutar script
sh database/run.sh

# Ejecutar imagen
docker run --network=emisa-network -itd --name emisa \
    -h emisa \
    -p 80:8080 \
    emisa


# Ver logs del proyecto
docker logs emisa

