# Despliegue con contenedores 

Para este hito se realizará el despliegue de la aplicación con contenedores, los cuales se han convertido en un herramienta para poder desplegar rápidamente aplicaciones y evitar los problemas de “en mi computador funciona”  los cuales son muy frecuentes en nuestra rama de ingeniería.

Los contenedores son básicamente una formas de encapsular las dependencias necesarias  de una aplicación, por ejemplo un sistema operativo o una herramienta como java, python o node con el fin que la aplicación pueda ejecutarse de una forma independiente del sistema operativo en el cual se despliega.

Si bien los contenedore no son una idea nueva, desde el 2012 se ha visto una adopción a lo largo de la industria de software, esto debido a las ventajas que ofrecen sobre otras métodos de infraestructuras como  los son los servidores fisicos o máquinas virtuales.

### Ventajas de los contenedores

- Despliegue  en un entorno repetible, es decir que el contenedor siempre va a ser el mismo evitando que los desarrolladores pasen menos tiempo depurando y configurando los entornos donde se  despliega una aplicación.

- Ejecución en cualquier lugar, ya sea un sistema operativo windows, linux o Mac pues los contenedores se ejecutan como procesos independientes sin importar donde se desplieguen.

- A diferencia de las máquinas virtuales las cuales necesitan de la instalación del sistema operativo completo, un contenedor solo necesita instalar las dependencias básicas para ejecutar una aplicación. 

    ![contenedoresvsmq](https://user-images.githubusercontent.com/24718808/52476691-b51ecd80-2b9f-11e9-8faa-620d0b3cf88f.png)


Para saber más sobre contenedores y sus aplicaciones puede ver este [video](https://www.youtube.com/watch?v=wuhxSLapDe0) y estos enlaces:

- https://blog.netapp.com/blogs/containers-vs-vms/

- http://jj.github.io/CC/documentos/temas/Contenedores

- https://cloud.google.com/containers/


## Docker 

[Docker](https://www.docker.com/) es una herramienta para el manejo de contenedores que se ha convertido en una de las más usadas para el manejo de contenedores, empezando como software libre sin embargo hoy existe una versión empresarial.

Después de seguir las instrucciones de instalación de la documentación oficial ,se puede comprobar que docker está instalado en la máquina ejecutando el comando `docker`. 


## Creando Contenedores 

En este hito se utiliza docker como herramienta para la creación de un contenedor para nuestro microservicios y  ademas se usa un contenedor de [postgreSql](https://hub.docker.com/_/postgres) para la base de datos, finalmente  se orquestan ambos contenedores con docker-compose. 

## Contenedor del sevicio usuario

Cada contenedor se crea como si fuera una lasaña  es decir por capas, la capa base para nuestro contenedor fue la imagen openjdk:8-jdk-alpine, la cual se  escoge porque tiene el jdk de java ya instalado y  además alpine es un sistema operativo ligero para dockerizar aplicaciones java.

### DockeFile

Para crear el contenedor es necesario crear primero el archivo [DockerFile](https://github.com/danielbc09/Proyecto_CC/blob/master/contenedores/Dockerfile). Nuestro archivo esta compuesto de los siguientes elementos.

Se crea el contenedor basado en la imagen openjdk:8-jdk-alpine, para esto es necesario que el dockerfile descargue esta imagen , se utiliza la orden reservada FROM con este fin.

~~~
FROM openjdk:8-jdk-alpine
~~~

Para saber quien le va a dar mantenimiento a la imagen o hacer mejoras se coloca un LABEL con el correo personal:

~~~
LABEL maintainer="danielbc@correo.ugr.es"
~~~

Se utiliza el volumen de datos en la ruta “/logs” para que la información de el contenedor persista, sin importar luego que se elimine el contenedor.

~~~
VOLUME [ "/logs" ]
~~~

Se crean variables de entorno dentro del contenedor, para que sean leídas por el framework springboot, y que se pueda conectar al contenedor de base de datos.

~~~
ENV JDBC_DATABASE_URL=url de la base de datos
ENV JDBC_DATABASE_USERNAME=nombre de usuario
ENV JDBC_DATABASE_PASSWORD=clave de la base de datos

~~~

Se expone el puerto 8080 para que el contenedor se ejecute en este puerto a la hora de ser creado.

~~~
EXPOSE 8080
~~~

Después de ejecutar el comando ` mvn clean package` el cual nos genera el .jar de nuestra aplicación “busapp-0.0.1-SNAPSHOT.jar” se copia en el mismo directorio en el cual esta el  Dockerfile y con ADD se añade a nuestro contenedor.

~~~
ADD busapp-0.0.1-SNAPSHOT.jar busapp-0.0.1-SNAPSHOT.jar
~~~

Se utiliza ENTRYPOINT para que al crear la aplicación se ejecute el .jar “busapp-0.0.1-SNAPSHOT.jar”

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

Se coloca la etiqueta a la imagen además de su nombre 

~~~
docker tag <id de la imagen> danielbc/userservice:latest
~~~ 

Finalmente se utiliza push para subir la imagen a docker hub

~~~
docker push danielbc/userservice:latest
~~~

La siguiente imagen muestra nuestro contenedor publicado:

![contenedor_publicado](https://user-images.githubusercontent.com/24718808/52476871-5148d480-2ba0-11e9-96c4-85d4f9206873.png)

[link del conenedor en docker hub](https://cloud.docker.com/u/danielbc/repository/docker/danielbc/userservice)


## Docker Compose

Esta herramienta de Docker permite definir y ejecutar  aplicaciones con múltiples contenedores como describe su [documentacion](https://docs.docker.com/compose/).

DockerCompose se utilizó con el objetivo de orquestar nuestros contenedores de microservicios creado anteriormente y una base de datos postgresql con sus respectivas credenciales.

Se utiliza un fichero YML [docker-compose.yml](https://github.com/danielbc09/Proyecto_CC/blob/master/contenedores/docker-compose.yml) en el cual se definen los dos servicios.

Se utiliza la versión 2 de composer, además como se muestra en el siguiente fragmento de código, se define mediante la palabra reservada `services` los servicios que el docker-composer va a orquestar.El primero es el servicio del contenedor que está alojado en docker hub con el nombre “danielbc/userservice” en el puerto 8080. La instrucción `depends_on` es muy importante ya que se define que el `servicio` userservice depende el servicio de `servicepostgres`, el cual es un servicio de bases de datos que se define más adelante.

~~~

services:
 userservice:
   image: danielbc/userservice
   ports:
     - "8080:8080"
   depends_on:
     - servicepostgres

~~~


El servicio de base de datos “servicepostgres” está definido para que descargue la imagen más utilizada de la base de datos postgreSql, se configura en el puerto convencional de postgres que es el “5432”. Además se crean el usuario, la base de datos y su respectiva clave, con el fin que los servicios desplegados en el primer contenedor puedan acceder a la bd y generar las tablas mediante el ORM Hibernate.


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


Ya con el fichero definido se ejecuta el comando `docker compose  --built`  que se encarga de descargar las imágenes y orquestar la aplicación para que pueda funcionar.

Para que el proceso no se quede estancado en la terminal , podemos agregarle -d con lo cual los contenedores se crean y ejecutan de una forma transparente o “background”.

~~~
docker compose -d --built`
~~~

En la siguiente imagen se puede ver el principio del resultado de la orquestación del contenedor ya que la imagen entera es muy grande para colocarla.

![composer_up](https://user-images.githubusercontent.com/24718808/52477318-bbae4480-2ba1-11e9-9b60-1631a7c172c8.png)


## Despliegue Azure

Para desplegar en azure se crea una máquina virtual Ubuntu Server 18.04 LTS de tamaño Básico A0 (1 vcpu, 0.75 GB de memoria).

![maquina_virtual](https://user-images.githubusercontent.com/24718808/53039285-2a7f7d80-347f-11e9-89ed-b4b11bd7daac.png)

Se instala docker y docker composer en la máquina y se siguen las instrucciones para desplegar los contenedores que se mostraron en docker-compose. 

El resultado de nuestros contenedores ejecutándose se puede ver con el comando ` sudo docker ps `

![contenedores_azure](https://user-images.githubusercontent.com/24718808/52477340-d08ad800-2ba1-11e9-8d91-4852aa452e0b.png)


Finalmente en la siguiente imagen se puede ver la aplicación ejecutándose en la máquina  http://20.188.37.138/ nos muestra el siguiente resultado.

![evidancia_web](https://user-images.githubusercontent.com/24718808/53039403-6f0b1900-347f-11e9-9cb8-4cf0c875434c.png)


## Evidencias Microservicios

### Microservicios de Usuarios
Se realiza las operaciones para el microservicio de [usuarios](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#usuarios)

Obtención de usuarios

![prueba_get_users](https://user-images.githubusercontent.com/24718808/53039992-9e6e5580-3480-11e9-8571-4b8fc914adce.png)

Creación de usuarios:

![ususario_creado](https://user-images.githubusercontent.com/24718808/53040017-b2b25280-3480-11e9-9168-fe7ddcb0967d.png)

Eliminación de usuarios:

![usuario_borrado](https://user-images.githubusercontent.com/24718808/53040072-d2497b00-3480-11e9-82d8-41ff880bc01c.png)

### Microservicios de Compras
El microservicio de compras esta documentado en este [link](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#compra)

Realizar una compra de un tiquete:

![compra](https://user-images.githubusercontent.com/24718808/53040114-ed1bef80-3480-11e9-8c75-f42d1b26d695.png)

Obtener las compras hechas:

![compra_get](https://user-images.githubusercontent.com/24718808/53040140-0329b000-3481-11e9-8619-b6b9d6b0f95b.png)

### Microservicios Tiquetes
El microservicio de tiquetes esta documentado en este [link](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#tiquetes)

Se realiza la obtención de todos los tiquetes:

![tickets_get](https://user-images.githubusercontent.com/24718808/53040404-9b279980-3481-11e9-91fa-3a1f12af671c.png)


## Referencias
https://docs.docker.com/compose/ 
https://spring.io/guides/topicals/spring-boot-docker/
http://jj.github.io/CC/documentos/temas/Contenedores
https://docs.docker.com/engine/examples/postgresql_service/
https://blog.netapp.com/blogs/containers-vs-vms/

