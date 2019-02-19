# BUSAPP


![Status](https://img.shields.io/badge/Status-Documenting-yellow.svg)  [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Language](https://img.shields.io/badge/laguage-java-green.svg)](https://www.java.com/)
[![Framework](https://img.shields.io/badge/framework-spring-yellowgreen.svg)](https://spring.io/)
[![Build Status](https://travis-ci.org/danielbc09/Proyecto_CC.svg?branch=master)](https://travis-ci.org/danielbc09/Proyecto_CC)


### Autor

* Jairo Daniel Bautista Castro

* [Página del proyecto](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md)

## Descripción del Proyecto

El proyecto consiste en realizar una aplicación para realizar pagos de tiquetes de buses desde el móvil, con esto el usuario no tendrá que estar recargando 
las tarjetas de los buses y los conductores no perderán el tiempo en cada estación recargando tarjetas y devolviendo cambio a los usuarios.

[mas Información del proyecto:](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#descripcion)
## Definición de Arquitectura

El proyecto se utiliza  una arquitectura distribuida de Microservicios. Estos microservicios se  desarrollaran en el lenguaje Java con el Framerwork Spring Boot.
    
   
[mas Información de la arquitectura del proyecto:](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#arquitectura)

## Despliegue Heroku 

Despliegue: https://jdbusapp.herokuapp.com/

Se despliega la aplicación en la nube de Heroku, con la ayuda del version de controles gitHub y la herramienta de 
integración continua travis CI.

El servicio que se despliega es el de usuarios. El cual se encarga de realizar un CRUD del servicio.
[mas Información del despliegue en Heroku](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/2_heroku_despliegue.md)

## API REST

Para mas información del API Rest de cada Microservicio dirijase al siguiente [link](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#desarrollo)


## Provisionamiento de maquinas virtuales

Para el aprovisionamiento de la Maquina Virtual se ha usado Ansible. Mediante el PaaS Azure.
 
MV: 168.62.51.36

Para mayor información sobre el la maquina virtual en azure [aquí](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/aprovisionamiento.md)

## Automatización

MV2: 20.188.38.50

Se crea el script de "acopio.sh" con el objetivo de automatizar la creación de una maquina virtual desde la consolo de clientes de la plataforma PaaS azure.

Para mas información sobre por favor dirjase al siguiente [link](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/automatizacion.md)

## Orquestación Con Vagrant

Despliegue Vagrant: 40.89.155.201


Para mas información del  despliegue con vagrant por favor diríjase a este [link](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/5_orquestacion.md).

Se ha comprobado la orquestación de El compañero Miguel gonCalves en el [siguiente documento](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/5_comprobacion.md).

El compañero Miguel ha comprobado mi orquestación, para más información dirigirse a el siguiente [link](https://github.com/migueldgoncalves/CCproj_1819/blob/master/docs/Comprobacion_Hito5/comprobacion_provisionamiento_MiguelGoncalves_2.md).

Los avances del Hito 5 estan en el siguiente [documento](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/5_avances.md).


## Contenedores 


Contenedor: http://20.188.37.138/

DockerHub: https://hub.docker.com/r/danielbc/userservice

Para este hito se utilizo la herramienta docker para la creación de nuestro contenedor en el cual se ejecuta el microservicio de usuario, ademas  con ayuda de docker-compose se realiza una orquestación con otro contenedor con una base de datos postgresql.

La documentación del proceso se encuentra en el siguiente [enlace](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/6_contenedores.md)

