# Despliegue con contenedores 

Para este hito se realizará el despliegue de la aplicación con contenedores, lo cuales se han convertido en un herramienta extra para poder desplegar rápidamente aplicaciones y evitar lo problemas de “en mi computador funciona”  los cuales son muy frecuentes en nuestra rama de ingeniería.

Los contenedores son básicamente  es una formas de encapsular las dependencias necesarias  de una aplicación por ejemplo un sistema operativo, o una herramienta como java python o node , con el fin que la aplicación pueda ejecutarse de una forma independiente  del sistema operativo en el cual se despliega.

Si bien los contenedore no son una idea nueva , desde el 2012 se ha visto una adopción a lo largo de la industria de software , esto debido a las ventajas que ofrecen sobre otras métodos como despliegue en un servidor físico o en máquinas virtuales.

### Ventajas de los contenedores son

- Despliegue  en un entorno repetible, es decir que el contenedor siempre va a ser el mismo , evitando que los desarrolles pasen menos tiempo depurando y configurando los entornos donde se  despliega una aplicación.

- Ejecución en cualquier lugar, ya sea un sistema operativo windows, linux o Mac. ya que son procesos independientes de donde se desplieguen.

- A diferencia de las máquinas virtuales que se necesita instalar el sistema operativo completo , un contenedor solo necesita instalar las dependencias básicas para ejecutar una aplicación. 
imagen de 

    ![contenedoresvsmq](https://user-images.githubusercontent.com/24718808/52476691-b51ecd80-2b9f-11e9-8faa-620d0b3cf88f.png)


Para saber más sobre contenedores y sus aplicaciones puede ver este [video](https://www.youtube.com/watch?v=wuhxSLapDe0) y estos enlaces:

- https://blog.netapp.com/blogs/containers-vs-vms/

- http://jj.github.io/CC/documentos/temas/Contenedores

- https://cloud.google.com/containers/


## Docker 

[Docker](https://www.docker.com/) es una herramienta para el manejo de contenedores , es una de las herramientas más usadas para el manejo de contenedores y existe la versión empresarial como libre.

Después de seguir las instrucciones de instalación de la documentación oficial ,se puede comprobar que docker está instalado en la máquina ejecutando el comando `docker`. 


## Creando Contenedores 

Para este hito se utiliza docker como herramienta para crear contenedores de nuestro microservicio de usuarios  además se utiliza un contenedor de postgreSql para la base de datos y se orquestan con docker-compose. 

## Contenedor del sevicio usuario

Cada contenedor se crea como si fuera una lasaña  es decir por capas , la capa base para nuestro contenedor fue la imagen openjdk:8-jdk-alpine, la cual se  escoge porque tiene el jdk de java ya instalado, además que alpine es un sistema operativo ligero para dockerizar aplicaciones java.

### DockeFile

Para crear el contenedor es necesario crear primero el archivo [DockerFile](https://github.com/danielbc09/Proyecto_CC/blob/master/contenedores/Dockerfile)   . Nuestro archivo conesta compuesto de los siguientes elementos.

Primero se crea el contenedor basado en la imagen openjdk:8-jdk-alpine, para esto es necesario que el dockerfile descargue esta imagen , se utiliza la orden reservada FROM con ese fin

~~~
FROM openjdk:8-jdk-alpine
~~~

Para saber quien le va a dar mantenimiento a la imagen o hacer mejoras se coloca un LABEL con el correo personal:

~~~
LABEL maintainer="danielbc@correo.ugr.es"
~~~

Se utiliza el volumen de datos en la ruta “/logs” para que la información de el contenedor persista , aunque se elimine el contenedor.

~~~
VOLUME [ "/logs" ]
~~~

Se crean variables de entorno dentro del contenedor , para que sean leídas por el framework springboot , y poderse conectar al contenedor de base de datos.

~~~
ENV JDBC_DATABASE_URL=url de la base de datos
ENV JDBC_DATABASE_USERNAME=nombre de usuario
ENV JDBC_DATABASE_PASSWORD=clave de la base de datos

~~~

Se expone el puerto 8080 para que el contenedor se ejecute en este puerto a la hora de ser creado.

~~~
EXPOSE 8080
~~~

Después de ejecutar el comando ` mvn clean package` el cual nos genera el .jar de nuestra aplicación “busapp-0.0.1-SNAPSHOT.jar” se copia en el mismo directorio del Dockerfile y con ADD se añade a nuestro contenedor.

~~~
ADD busapp-0.0.1-SNAPSHOT.jar busapp-0.0.1-SNAPSHOT.jar
~~~

Se utiliza ENTRYPOINT para , que al crear la aplicación se ejecute el .jar “busapp-0.0.1-SNAPSHOT.jar”

~~~
ENTRYPOINT [ "java", "-jar", "/busapp-0.0.1-SNAPSHOT.jar" ]
~~~

Se construye el contenedor con el comando ` docker build -t daniel/dockerservice . ` como se muestra en la siguiente imagen: 


![construir_contenedor](https://user-images.githubusercontent.com/24718808/52476821-2cecf800-2ba0-11e9-9a82-1626e66fcdd7.png)


## Publicando nuestro contenedor.

Para esto se creo una cuenta en [docker hub],  y se siguen los siguientes pasos:

Se hace login con la cuenta de usuario

~~~
docker login
~~~

Se coloca la etiqueta a a la imagen además de su nombre 

~~~
docker tag <id de la imagen> danielbc/userservice:latest
~~~ 

Finalmente se utiliza push para subir la imagen a docker hub

~~~
docker push danielbc/userservice:latest
~~~

La siguiente imagen nos muestra nuestro contenedor publicado:

![contenedor_publicado](https://user-images.githubusercontent.com/24718808/52476871-5148d480-2ba0-11e9-96c4-85d4f9206873.png)

[link del conenedor en docker hub](https://cloud.docker.com/u/danielbc/repository/docker/danielbc/userservice)


## Docker Compose

Esta herramienta de Docker permite definir y ejecutar  aplicaciones con múltiples contenedores como describe su [documentacion](https://docs.docker.com/compose/).

Esta Herramienta se utilizó con el objetivo de orquestar nuestros contenedores de servicios de usuario y una base de datos Postgresql con sus respectivas credenciales.

Se utiliza un fichero YML [docker-compose.yml](https://github.com/danielbc09/Proyecto_CC/blob/master/contenedores/docker-compose.yml) en el cual se definen los dos servicios.

Se utiliza la versión 2 de composer, además como se muestra en el siguiente fragmento de código , se define mediante la palabra reservada `services` los servicios que el docker-composer va a orquestar, el primero es el servicio del contenedor que está alojado en docker hub con el nombre “danielbc/userservice”, en el puerto 8080.  La instrucción `depends_on` es muy importante ya que se define que el `servicio` userservice depende el servicio de `servicepostgres`, el cual es un servicio de bases de datos que se define más adelante.

~~~

services:
 userservice:
   image: danielbc/userservice
   ports:
     - "8080:8080"
   depends_on:
     - servicepostgres

~~~


El servicio de base de datos “servicepostgres” está definido para que descargue la imagen más utilizada de la base de datos postgreSql, se configura en el puerto convencional de postgres que es el “5432” . Además se crean el usuario, la base de datos y su respectiva clave , con el fin que el servicio usuarios pueda acceder a ella y generar las tablas mediante el ORM Hibernate.


~~~
 servicepostgres:
   image: postgres
   restart: always
   ports:
     - "5432:5432"
   environment:
     - POSTGRES_DB=nombre_de_DB
     - POSTGRES_USER=usuario_bd
     - POSTGRES_PASSWORD=clave_bd
   volumes:
     - ./data/postgres:/var/lib/postgresql/data
~~~


Ya con este fichero definido solo es ejecutar el comando `docker compose  --built`  que se encarga de descargar las imágenes y orquestar la aplicación para que pueda funcionar.

Para que el proceso no se quede estancado en la terminal , podemos agregarle -d, para que los contenedores se creen y ejecuten de una forma transparente o “background”.

~~~
docker compose -d --built`
~~~

En la siguiente imagen se puede ver el el principio del resultado de la orquestación del contenedor ya que la imagen entera es muy grande para colocarla.

![composer_up](https://user-images.githubusercontent.com/24718808/52477318-bbae4480-2ba1-11e9-9b60-1631a7c172c8.png)


## Despliegue Azure

Para desplegar en azure se crea una máquina virtual Ubuntu Server 18.04 LTS de tamaño Básico A0 (1 vcpu, 0.75 GB de memoria) .

![maquina_virtual_azure_1](https://user-images.githubusercontent.com/24718808/52477242-873a8880-2ba1-11e9-81b7-4edd21d7e2aa.png)

Se instala docker y docker composer en la máquina y se siguen las instrucciones para desplegar los contenedores que se mostraron en docker-compose. 

El resultado de nuestros contenedores ejecutándose se puede ver con el comando ` sudo docker ps `

![contenedores_azure](https://user-images.githubusercontent.com/24718808/52477340-d08ad800-2ba1-11e9-8d91-4852aa452e0b.png)


Finalmente en la siguiente imagen se puede ver la aplicación ejecutándose en la máquina  http://20.188.37.138/ nos muestra el siguiente resultado.

![prueba_docker](https://user-images.githubusercontent.com/24718808/52477358-e00a2100-2ba1-11e9-8767-80651cfc9265.png)


## Referencias

https://docs.docker.com/compose/ 
https://spring.io/guides/topicals/spring-boot-docker/
http://jj.github.io/CC/documentos/temas/Contenedores
https://docs.docker.com/engine/examples/postgresql_service/
https://blog.netapp.com/blogs/containers-vs-vms/

