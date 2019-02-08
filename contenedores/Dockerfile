# Se selecciona la imagen alpine con el jdk 8 ya listo
FROM openjdk:8-jdk-alpine
#
LABEL maintainer="danielbc@correo.ugr.es"

# Volumen del contenedor

VOLUME [ "/logs" ]

# Variables de entorno para la conección con la base base de datos
ENV JDBC_DATABASE_URL=jdbc:postgresql://servicepostgres:5432/ccproject
ENV JDBC_DATABASE_USERNAME=Springboot
ENV JDBC_DATABASE_PASSWORD=1234

# Exponer el puerto 8080
EXPOSE 8080

# Añadir el jar de la aplicacion el cual se va a dockerizar
ADD busapp-0.0.1-SNAPSHOT.jar busapp-0.0.1-SNAPSHOT.jar

# Ejecutar la aplicacion en el docker.
ENTRYPOINT [ "java", "-jar", "/busapp-0.0.1-SNAPSHOT.jar" ]




