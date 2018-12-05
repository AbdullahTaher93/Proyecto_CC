# BUSAPP


![Status](https://img.shields.io/badge/Status-Documenting-yellow.svg)  [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Language](https://img.shields.io/badge/laguage-java-green.svg)](https://www.java.com/)
[![Framework](https://img.shields.io/badge/framework-spring-yellowgreen.svg)](https://spring.io/)
[![Build Status](https://travis-ci.org/danielbc09/Proyecto_CC.svg?branch=master)](https://travis-ci.org/danielbc09/Proyecto_CC)


### Autor:

* Jairo Daniel Bautista Castro

* [Página del proyecto](https://danielbc09.github.io/Proyecto_CC/Documentacion)

## Descripción del Proyecto

El proyecto consiste en realizar una aplicación para realizar pagos de tiquetes de buses desde el móvil, con esto el usuario no tendrá que estar recargando 
las tarjetas de los buses y los conductores no perderán el tiempo en cada estación recargando tarjetas y devolviendo cambio a los usuarios.


## Definición de Arquitectura

El proyecto se desarrollara con una arquitectura distribuida de Microservicios. Estos microservicios se 
desarrollaran en el lenguaje Java con el Framerwork Spring Boot.

Los Microserivicios que se proponen para el sistemas son los siguientes:

   - Gestion de usuarios (microservicio encargado de manejar el login y los usuarios de la aplicación).
   - Procesamiento de pagos (microservicio encargado de gestionar el pago de los tiquetes).
   - Gestion y validación de tiquetes(Se encargará, de gestionar la compra y validacion de los iquetes).
   - Rutas y Estaciones (microservicio encargado de mostrar las rutas y estaciones degranada).
   - Servicio para manejo de logs.
    

## Desarrollo

El proyecto está desarrollado en el lenguaje Java con el Framework Spring Boot , se escoge este framework ya que es fácil crear aplicaciones API Rest y su configuración es 
relativamente sencilla.Este Framework se enfoca en las buenas prácticas propuestas por en el AOP([Aspect Oriented Programming](https://docs.spring.io/spring/docs/4.3.15.RELEASE/spring-framework-reference/html/aop.html)), 
el cual promueve prácticas de desarrollo con bajo acoplamiento, dividiendo capas como Repositorios, Servicios , y  Modelos de negocio.
Además de las buenas prácticas que se proponen en este Framework es fácil de configurar con cualquier base de datos , en este caso utilizando Hibernate como ORM 
se puede configurar el acceso a  la Base de datos [PostgreSQL](https://www.postgresql.org/).
   
[mas Información del proyecto:](https://danielbc09.github.io/Proyecto_CC/Documentacion)


## Test Aplicación:

Se va a realizar el desarrollo basado en pruebas unitarias, cada método tendrá su test unitario, se espera realizar un cubrimiento de código entre el 75 y 90 por ciento de la 
aplicación con pruebas unitarias.
Spring Framework con su manejo de Inyección de dependencias  nos ayuda a desarrollar código que sea fácil de probar. Utilizando el [Framework Junit](https://junit.org/junit5/) 
para las pruebas unitarias , además de [Mockito](https://site.mockito.org/) para manejar una sintaxis estilo BDD y realizar los Mocks, nos da herramientas para poder crear una 
aplicación bien probada.

En cuanto a las pruebas funcionales de las API Rest se utiliza la herramienta [PostMan](https://www.getpostman.com/).


## Despliegue

Despliegue: https://jdbusapp.herokuapp.com/

Se despliega la aplicación en la nube de Heroku, con la ayuda del version de controles gitHub y la herramienta de 
integración continua travis CI.

El servicio que se despliega es el de usuarios. El cual se encarga de realizar un CRUD del servicio.


### API REST

El servicio de Usuarios expone las operaciones de gestión de los usuarios de la aplicación , estas funcionalidades son Básicamente un CRUD de usuarios el cual nos permite Crear,Obtener,  Actualizar , Y eliminar un usuario. 

Los datos son devueltos en formato Json con las diferentes respuesta para el  usuario.

 
    * GET "/" : Que nos devolvuelve una ruta ejemplo
    
    * GET "/user"  : Nos devolvuelve todos los usuarios
    
    * GET "/user/{id}" : Nos regresa un usuario basado en el Id.
    
    * POST "/user" : Crea un usuario si se envia los parametros correctamente.
    
    * PUT "/user/{id}" : Modifica un usuario basado el Id el mismo.
    
    * DELETE "/user/{id}": Se elimina un usuario basado en el Id.

### Infraestructura

Los archivos de configuracion para la infraestructura de la aplicacion en Heroku son los siguentes:

   - [procfile.txt](https://github.com/danielbc09/Proyecto_CC/blob/master/Procfile): En el procfile se encuentra la   configuracion necesaria de Heroku para que pueda Funcionar Spring en java, web: java -Dserver.port=$PORT $JAVA_OPTS -jar target/busapp-0.0.1-SNAPSHOT.jar.
   
   - [.travis.yml](https://github.com/danielbc09/Proyecto_CC/blob/master/.travis.yml): En el .travis.yml esta la configuración la cual solamente es instalar java y el jdk8.

###  Travis.

Para travis aparte de configurar el .travis.yml , se tiene que ingresar a la pagina web y conectar la cuenta de travis con la cuenta de github, despues se selecciona el repositorio al cual se quiere que se hagan los test.

![travis_ci](https://user-images.githubusercontent.com/24718808/49330602-2daa5200-f591-11e8-8f6f-fe11e2fc87c9.png)


La siguiente imagen muestra el build exitoso en travis CI , para mas informaciń ir al siguiente [link](https://travis-ci.org/danielbc09/Proyecto_CC/jobs/462217719).

![travis_ci](https://user-images.githubusercontent.com/24718808/49340508-03b06880-f641-11e8-9414-fec5373f2ce9.png)


### Heroku 

En Heroku se configura una instancia de la aplicación mas un add-ons de bases de datos postgreSQL como lo muestra la siguiente imagen:

![heroku_postgress](https://user-images.githubusercontent.com/24718808/49340543-74578500-f641-11e8-83c1-4a3db11da5ee.png)


para que la aplicación se pueda conectar a la base de datos , se raliza una configuración en el archivo [aplication.properties del](https://github.com/danielbc09/Proyecto_CC/blob/master/src/main/resources/application.properties) proyecto de las variables de entorno:

![env_variables](https://user-images.githubusercontent.com/24718808/49340565-d7e1b280-f641-11e8-9da5-7ef521f43027.png)

Finalmente para que se realice el despliegue en Heroku se configura en "Deploy" el repositorio el cual se quiere desplegar y se añade la opción que solo se despliegue despues de pasar los test de travis CI.

![heroku_github](https://user-images.githubusercontent.com/24718808/49340603-82f26c00-f642-11e8-8ee9-8813efb33697.png)


## Provisionamiento de maquinas virtuales

Se ha creado una maquina virtual en azure Ubuntu Server 18.04 LTS. En el [fichero](https://github.com/danielbc09/Proyecto_CC/blob/master/provision/provision.yml) esta la configuracin necesaria para   
 esta la configuración necesaria para la instalación de la infraestructura necesaria para nuestra aplicación , asi mismo como el despliegue.
 

se ejecuta el comando nsible-playbook -i ansible_hosts -b ansible-playbook provision.yml -v

![instalacion](https://user-images.githubusercontent.com/24718808/49507929-cf77ba80-f881-11e8-85df-bd889e6c9e78.png)


finalmente la maquina responde:

![despliegue](https://user-images.githubusercontent.com/24718808/49507922-cbe43380-f881-11e8-903a-409346f3693d.png)

La dirección IP: 168.62.51.36

MV: http://168.62.51.36/

Para mayor información sobre el despliegue [aquí](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/aprovisionamiento.md)




   
