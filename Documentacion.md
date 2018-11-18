# Descripción del Proyecto

El proyecto consiste en realizar una aplicación para realizar pagos de tiquetes de buses desde el móvil, con esto el usuario no tendrá que estar recargando 
las tarjetas de los buses y los conductores no perderán el tiempo en cada estación recargando tarjetas y devolviendo cambio a los usuarios.

Un usuario puede descargar la aplicación en el celular y podrá seleccionar la estación de origen y destino , con esto la aplicación calculara el valor del
tiquete y le dará una opción de pago al usuario, puede ser tarjeta crédito o con PSE.Luego de que el tiquete sea comprado ,por medio de tecnología NFS lo 
presentara para que sea validado y pueda acceder al bus.

# Definición de Arquitectura

El proyecto se desarrollara con una arquitectura distribuida de Microservicios, para ello se escogió el Lenguaje Java y el Framerwork Spring Boot, además se utilizará 
herramientas de netflix para ayudar a la gestión de los microservicios como Eureka, Ribbon y Zuul.

La aplicación por ahora esta prevista en desplegarse en Heroku, utilizando contenedores Docker y Herramientas para manejar la infraestructura será ansible.

Para cada microservicio se utilizará el patrón de una base de datos por microservicio. Ya que la aplicación será desplegada en Heroku la base de datos que se escogerá
es postgreSQL. 

Los Microserivicios que se proponen para el sistemas son los siguientes:

    - Gestion de usuarios (microservicio encargado de manejar el login y los usuarios de la aplicación).
    - Procesamiento de pagos (microservicio encargado de gestionar el pago de los tiquetes).
    - Gestion y validación de tiquetes(Se encargará, de gestionar la compra y validacion de los iquetes).
    - Rutas y Estaciones (microservicio encargado de mostrar las rutas y estaciones degranada).
    - Servicio para manejo de logs.

El cliente del proyecto sere yo. 

# Hitos del proyecto 

A lo largo del proyecto se tienen presente los siguientes hitos, sin embargo es posible que cambien a lo largo del proyecto


Hito 0 - Uso correcto de Git y GitHub [link](https://github.com/danielbc09/Proyecto_CC/milestone/1)

Hito 1 - Elección de un proyecto [link](https://github.com/danielbc09/Proyecto_CC/milestone/2)

Hito 2 - Creación y Despliegue primeros servicios del proyecto [link](https://github.com/danielbc09/Proyecto_CC/milestone/3)

Hito 3 - Aprovicionamiento de maquinas virtuales [link](https://github.com/danielbc09/Proyecto_CC/milestone/4)

Hito 4 - Definición y configuración de la herramienta para despliegue [link](https://github.com/danielbc09/Proyecto_CC/milestone/5)

Hito 5 - Aprovisionar las maquinas virtuales. [link](https://github.com/danielbc09/Proyecto_CC/milestone/6)

Hito 6 - Empezar desarrollo de los microservicios. [link](https://github.com/danielbc09/Proyecto_CC/milestone/7)

Hito 7 - Orquestar las maquinas virtuales [link](https://github.com/danielbc09/Proyecto_CC/milestone/8)

Hito 8 - Desarrollo e implementación de los contenedores. [link](https://github.com/danielbc09/Proyecto_CC/milestone/9)

Hito 9 - Entrega del MVP (minimum viable product) al cliente. [link](https://github.com/danielbc09/Proyecto_CC/milestone/10)



# Despliegue

Se despliega la aplicación en la nube de Heroku, con la ayuda del version de controles gitHub y la herramienta de integración continua travis CI.

El servicio que se despliega es el de usuarios. El cual se encarga de realizar un CRUD del servicio.


# API REST

EL servicio Usuarios tendra los siguientes metodos:
 
    * GET "/" : Que nos devolvuelve una ruta ejemplo
    
    * GET "/user"  : Nos devolvuelve todos los usuarios
    
    * GET "/user/{id}" : Nos regresa un usuario basado en el Id.
    
    * POST "/user" : Crea un usuario si se envia los parametros correctamente.
    
    * PUT "/user/{id}" : Modifica un usuario basado el Id el mismo.
    
    * DELETE "/user/{id}": Se elimina un usuario basado en el Id.
    
    