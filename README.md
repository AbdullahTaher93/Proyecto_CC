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
    
   
[mas Información del proyecto:](https://danielbc09.github.io/Proyecto_CC/Documentacion)

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

[mas Información del despliegue](https://danielbc09.github.io/Proyecto_CC/despliegue)



## Provisionamiento de maquinas virtuales

Para el aprovisionamiento de la Maquina Virtual se ha usado Ansible. Mediante el PaaS Azure.
 
MV: 168.62.51.36

Para mayor información sobre el la maquina virtual en azure [aquí](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/aprovisionamiento.md)

## Automatización

MV2: 20.188.32.177

Se crea el script de "acopio.sh" con el objetivo de automatizar la creación de una maquina virtual desde la consolo de clientes de la plataforma PaaS azure.

Para mas información sobre por favor dirjase al siguiente [link](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/automatizacion.md)

