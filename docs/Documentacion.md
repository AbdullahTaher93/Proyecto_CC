** Indice **
   
   1. [Descripción del proyecto](#descripcion)
   2. [Arquitectura del proyecto](#arquitectura)
   3. [Desarrollo](#desarrollo)
   4. [Test de la Aplicación](#test)
   5. [Hitos Del Proyecto](#hitos)
   
   

# Descripción del Proyecto <a name="descripcion"></a>

El proyecto consiste en realizar una aplicación para realizar pagos de tiquetes de buses desde el móvil, con esto el usuario no tendrá que estar recargando 
las tarjetas de los buses y los conductores no perderán el tiempo en cada estación recargando tarjetas y devolviendo cambio a los usuarios.

Un usuario puede descargar la aplicación en el celular y podrá seleccionar la estación de origen y destino , con esto la aplicación calculara el valor del
tiquete y le dará una opción de pago al usuario, puede ser tarjeta crédito o con PSE.Luego de que el tiquete sea comprado ,por medio de tecnología NFS lo 
presentara para que sea validado y pueda acceder al bus.


# Definición de Arquitectura <a name="arquitectura"></a>

El proyecto se desarrollara con una arquitectura distribuida de Microservicios, para ello se escogió el Lenguaje Java y el Framerwork Spring Boot, además se utilizará herramientas de netflix para ayudar a la gestión de los microservicios como Eurek y Zuul.

Eureka es una API de Netflix la cual nos ayuda a realisar el registro y descubrimiento de los microservicios,estos se registran en esta aplicación y luego eureka realiza el balanceo de carga para la comunicación entre los Microservicios registrados.

Zuul nos ayuda a controlar las peticiones externas de nuestros clientes, colocando una capa de seguridad y autenticación, ayuda a monitorizar las peticiones externas y ayuda a realizar el enrutamiento dinámico para nuestro microservicios.

La aplicación se desplegara en diferentes plataformas de servicios Paas como por ejemplo Heroku y azure, la idea es que esta aplicación se pueda desplegar en cualquier nube sin que afecte el rendimiento de la misma. 

Para cada microservicio se utilizará el patrón de una base de datos por microservicio. Ya que la aplicación será desplegada en Heroku la base de datos que se escoger, por ahora se tiene previsto realizar los cuatro microsrvicios en Java ,con el Framerwork Spring Boot.

La base de datos para cada microservicio puede variar, sin embargo está pensado que la aplicación maneje las siguientes bases de datos : PostgresSQL, MySql y MongoDB.abitMQ para el manejo y encolamiento de mensajes.

Los Microserivicios que se proponen para el sistemas son los siguientes:

    - Gestión de usuarios (microservicio encargado de manejar el login y los usuarios de la aplicación).
    - Procesamiento de pagos (microservicio encargado de gestionar el pago de los tiquetes).
    - Gestión y validación de tiquetes(Se encargará, de gestionar la compra y validacion de los iquetes).
    - Rutas y Estaciones (microservicio encargado de mostrar las rutas y estaciones degranada).
    - Servicio para manejo de logs.

Imagen Arquitectura:


![arquitectura_app](https://user-images.githubusercontent.com/24718808/49256160-6978d680-f42e-11e8-8fbb-59359542db3b.jpg)



## Desarrollo <a name="desarrollo"></a>

El proyecto está desarrollado en el lenguaje Java con el Framework Spring Boot , se escoge este framework ya que es fácil crear aplicaciones API Rest y su configuración es 
relativamente sencilla.Este Framework se enfoca en las buenas prácticas propuestas por en el AOP([Aspect Oriented Programming](https://docs.spring.io/spring/docs/4.3.15.RELEASE/spring-framework-reference/html/aop.html)), 
el cual promueve prácticas de desarrollo con bajo acoplamiento, dividiendo capas como Repositorios, Servicios , y  Modelos de negocio.
Además de las buenas prácticas que se proponen en este Framework es fácil de configurar con cualquier base de datos , en este caso utilizando Hibernate como ORM 
se puede configurar el acceso a  la Base de datos [PostgreSQL](https://www.postgresql.org/).


## Test Aplicación <a name="test"></a>

Se va a realizar el desarrollo basado en pruebas unitarias, cada método tendrá su test unitario, se espera realizar un cubrimiento de código entre el 75 y 90 por ciento de la 
aplicación con pruebas unitarias.
Spring Framework con su manejo de Inyección de dependencias  nos ayuda a desarrollar código que sea fácil de probar. Utilizando el [Framework Junit](https://junit.org/junit5/) 
para las pruebas unitarias , además de [Mockito](https://site.mockito.org/) para manejar una sintaxis estilo BDD y realizar los Mocks, nos da herramientas para poder crear una 
aplicación bien probada.

En cuanto a las pruebas funcionales de las API Rest se utiliza la herramienta [PostMan](https://www.getpostman.com/).

# Hitos del proyecto <a name="hitos"></a>

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

    
    
[despliegue](https://danielbc09.github.io/Proyecto_CC/despliegue)


