# Emisa

Trabajo para gestionar la base de datos Emisa, creada con el caso de estudio "Explotaciones Mineras".

## Estructura

### Vistas

Para la vista se utilizaron tecnologías web. Específicamente se usó el framework Vue.js para que
el desarrollo de la vista sea rápido y nos podamos enfocar en la gestión de la base de datos.

Los archivos relacionados a esta parte del programa se encuentran en la carpeta /WebContent/

## Controlador

Para ejecutar las operaciones desde el front-end se usó un servidor Tomcat, el cual se encuentra
en la carpeta /src.

El código que ejecuta los comandos sql en la base de datos se encuentra en /src/api/GZZ_Astro_Servlet.java.
Debido a que por el momento la funcionalidad es sencilla, todo el código relevante se encuentra en el mismo
archivo.

