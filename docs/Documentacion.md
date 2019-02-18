# Indice
   
   - [Descripción del proyecto](#descripcion)
   - [Arquitectura del proyecto](#arquitectura)
   - [Desarrollo](#desarrollo)
        - [Microservicio Usuarios](#usuarios)
            - [Pruebas Microservicio Usuarios](#pruebas-usuario)
        - [Microservicio Gestión de Tiquetes](#tiquetes)
            - [Pruebas Microservicio Tiquetes](#pruebas-tiquetes)
        - [Microservicio de Compra de tiquetes](#compra)
   - [Test de la Aplicación](#test)
   - [Hitos Del Proyecto](#hitos)
   
   
<a name="descripcion"></a>
# Descripción del Proyecto 

El proyecto consiste en realizar una aplicación para realizar pagos de tiquetes de buses desde el móvil, con esto el usuario no tendrá que estar recargando 
las tarjetas de los buses y los conductores no perderán el tiempo en cada estación recargando tarjetas y devolviendo cambio a los usuarios.

Un usuario puede descargar la aplicación en el celular y podrá seleccionar la estación de origen y destino , con esto la aplicación calculara el valor del
tiquete y le dará una opción de pago al usuario, puede ser tarjeta crédito o con PSE.Luego de que el tiquete sea comprado ,por medio de tecnología NFS lo 
presentara para que sea validado y pueda acceder al bus.

<a name="arquitectura"></a>
# Definición de Arquitectura 

El proyecto se desarrollara con una arquitectura distribuida de Microservicios, para ello se escogió el Lenguaje Java y el Framerwork Spring Boot, además se utilizará herramientas de netflix para ayudar a la gestión de los microservicios como Eurek y Zuul.

Eureka es una API de Netflix la cual nos ayuda a realisar el registro y descubrimiento de los microservicios,estos se registran en esta aplicación y luego eureka realiza el balanceo de carga para la comunicación entre los Microservicios registrados.

Zuul nos ayuda a controlar las peticiones externas de nuestros clientes, colocando una capa de seguridad y autenticación, ayuda a monitorizar las peticiones externas y ayuda a realizar el enrutamiento dinámico para nuestro microservicios.

La aplicación se desplegara en diferentes plataformas de servicios Paas como por ejemplo Heroku y azure, la idea es que esta aplicación se pueda desplegar en cualquier nube sin que afecte el rendimiento de la misma. 

Para cada microservicio se utilizará el patrón de una base de datos por microservicio. Ya que la aplicación será desplegada en Heroku la base de datos que se escoger, por ahora se tiene previsto realizar los cuatro microsrvicios en Java ,con el Framerwork Spring Boot.

La base de datos para cada microservicio puede variar, sin embargo está pensado que la aplicación maneje las siguientes bases de datos : PostgresSQL, MySql y MongoDB.abitMQ para el manejo y encolamiento de mensajes.

Los Microserivicios que se proponen para el sistemas son los siguientes:

    - Gestión de usuarios (microservicio encargado de manejar el login y los usuarios de la aplicación).
    - Compra de tiquetes (microservicio encargado de gestionar el pago de los tiquetes).
    - Gestión y validación de tiquetes(Se encargará, de gestionar la compra y validacion de los iquetes).
    - Rutas y Estaciones (microservicio encargado de mostrar las rutas y estaciones degranada).
    - Servicio para manejo de logs.

Imagen Arquitectura:


![arquitectura_app](https://user-images.githubusercontent.com/24718808/49256160-6978d680-f42e-11e8-8fbb-59359542db3b.jpg)



<a name="desarrollo"></a>
## Desarrollo 

El proyecto está desarrollado en el lenguaje Java con el Framework Spring-Boot , se escoge este framework ya que es fácil crear aplicaciones API Rest y su configuración es 
relativamente sencilla.Este Framework se enfoca en las buenas prácticas propuestas por en el AOP([Aspect Oriented Programming](https://docs.spring.io/spring/docs/4.3.15.RELEASE/spring-framework-reference/html/aop.html)), 
el cual promueve prácticas de desarrollo con bajo acoplamiento, dividiendo capas como Repositorios, Servicios , y  Modelos de negocio.
Además de las buenas prácticas que se proponen en este Framework es fácil de configurar con cualquier base de datos , en este caso utilizando Hibernate como ORM 
se puede configurar el acceso a  la Base de datos [PostgreSQL](https://www.postgresql.org/).

### Conexion con la base de datos Postgresql

Para realizar la conexión con la base de datos, se utiliza el driver JPA de Spring boot en el (archivo)[https://github.com/danielbc09/Proyecto_CC/blob/master/src/main/resources/application.properties] en el cual
se configuran las siguientes propiedades:

~~~
##  Propiedades para el acceso a la base de datos basadas en variables de entorno de los usuarios, para evitar problemas de seguridad.

    spring.datasource.url=${JDBC_DATABASE_URL}
    spring.datasource.username=${JDBC_DATABASE_USERNAME}
    spring.datasource.password=${JDBC_DATABASE_PASSWORD}

# Se ejecuta esta instrucción para que cuando se reinicie el sistema se borren los datos y se vuelvan a crear desde 0 otra vez.
    spring.jpa.hibernate.ddl-auto=create-drop


~~~
<a name="usuarios"></a>
### Microservicios Usuarios 


Para este Microservicio se realiza un servicio para gestionar los usuarios de la aplicación, es un crud de usuarios en el cual se pueden crear añadir y actualizar nuestros usuarios 
de la aplicación.


La siguiente es el API Rest del mircoservicio.

~~~

    * GET "/user"  : Nos retorna todos los usuarios
    
    * GET "/user/{id}" : Nos regresa un usuario basado en el Id.
    
    * POST "/user" : Crea un usuario si se envía los parámetros correctamente.
    
    * PUT "/user/{id}" : Modifica un usuario basado el Id el mismo.
    
    * DELETE "/user/{id}": Se elimina un usuario basado en el Id.

~~~

<a name="pruebas-usuario"></a>
#### Pruebas Microservicio Usuarios

Las siguientes Imagenes muestran pruebas funcionales del API Rest del Microservicio de usuarios.

#### Get Usuarios

EL metodo GET en la ruta `/user` nos retorna los usuarios  de la aplicación.

![getusuarios](https://user-images.githubusercontent.com/24718808/52973910-0a3cb980-33c0-11e9-9e93-f9001de862f2.png)


EL metodo GET en la ruta `/user/{usuarioId}` nos muestra un usuario individual.

![usuario_exitoso](https://user-images.githubusercontent.com/24718808/52974036-73243180-33c0-11e9-8f52-e3740a27724c.png)


Si no existe el usuario con el Id nos muestra el siguiente error

~~~
{
    "timestamp": "2019-02-18T20:03:54.765+0000",
    "message": "Usuario con id :6 no encontrado",
    "details": "uri=/user/6",
    "httpCodeMessage": "Not Found"
}
~~~

#### Creación y Actualización de Usuarios


En metodo POST en la ruta `/user/{usuarioId}` nos permite crear los usuarios de la aplicación, sin embargo es necesario enviar una petición `JSON(application/json)` con los
siguientes valores:

~~~
{
    "name": "Usuario Nuevo",
    "userName": "nuevousuario",
    "email": "WillieDMorrison@superrito.com",
    "password" : "1234",
    "roles": [
        "ROLE_INVENTED"
    ]
}
~~~


Si se envian los parametros correctos obtenemos el siguiente resultado

![post_exitoso_usuarios](https://user-images.githubusercontent.com/24718808/52974614-76202180-33c2-11e9-9872-a4b7e0ac3304.png)

Si no se envían los valores obligatorios como userName name o  email , nos muestra la siguiente excepción

~~~
{
    "timestamp": "2019-02-18T20:08:51.444+0000",
    "message": "Argumentos ingresados no validos.",
    "details": "Alguno de los valores los cuales ingresó no están permitidos por favor ingrese los atributos de la entidad que va a agregar. ",
    "httpCodeMessage": "Bad Request"
}
~~~

Para actualizar los usuarios con el metodo PUT en la ruta `/user/{usuarioId}` , nos muestra esta imagen si encuentra al usuario y los parametros son exitosos.


![put_ususarios](https://user-images.githubusercontent.com/24718808/52974758-06f6fd00-33c3-11e9-9b82-6314da7c3f05.png)

#### Eliminación de Usuarios.

Finalmente con el método DELETE en la ruta `/user/{usuarioId}` se  borra a un usuario Dependiendo del ID que se ingrese. Si el usuario no existe nos muestra el siguiente error:

~~~
{
    "timestamp": "2019-02-18T20:14:08.329+0000",
    "message": "Usuario con id :20 no encontrado",
    "details": "uri=/user/20",
    "httpCodeMessage": "Not Found"
}
~~~
<a name="pruebas-usuario"></a>

### Microservicio Tiquetes

El microservicio de Gestión y validación de tiquetes nos muestra los datos de los tiquetes de bus como lo son la ruta del bus, el horario del bus, el precio del tiquete y la
cantidad de los tiquetes que nos quedan a la venta.

Su API rest es:

~~~

    * GET "/tickets"  : Nos retorna todos los tiquetes
    
    * GET "/tickets/{id}" : Nos regresa un tiquete basado en el Id.
   
~~~


<a name="pruebas-tiquetes"></a>
#### Pruebas Microservicio Tiquetes 


Las siguientes Imagenes muestran pruebas funcionales del API Rest del Microservicio de tiquetes.



#### Obtener Los tiquetes.

En la ruta `/tickets` podemos obtener todos nuestros tiquetes que existen en la aplicación.

![get_tiquetes](https://user-images.githubusercontent.com/24718808/52975713-cef1b900-33c6-11e9-9aaf-60608a6468be.png)

para obtener un tiquete individual se puede acceder mediante la ruta `/tickets/{id-tiquete}`, si el tiquete no existe , 
nos muestra el siguiente resultado.

![tiquete_no_encontrado](https://user-images.githubusercontent.com/24718808/52975904-92728d00-33c7-11e9-8011-0d670e62c7de.png)



<a name="compras"></a>
### Microservicio de Compra de tiquetes

El microservicio de gestión de compras de tiquetes es el encargado de la realización de la venta de tiquetes para los usuarios, 
los datos necesarios son , el ID del usuario el cual va a comprar el tiquete, el ID del tiquete y la cantidad de tiquetes a comprar.

También este servicio válida si existen los suficientes tiquetes en inventario para la realización de la compra.
Finalmente  muestra las compras que se han hecho de los tiquetes los datos que muestra son,  el ID de la compra , el ID y el nombre del cliente , la ruta del bus, el precio por unidad de cada tiquete, la cantidad de tiquetes  comprados y el precio total de los tiquetes.


Su API rest es:

~~~
    * GET "/compras"  : Nos retorna las compras hechas en el sistema.
    
    * POST "/compras/{id-del-usuario}/{id-tiquete}/cantidad" : Metodo para realizar la compra de un tiquete
~~~

<a name="pruebas-compra"></a>
#### Pruebas Microservicio de compra de tiquetes

La pruebas funcionales del microservicio son las siguientes:

#### Obtener Las Compras

para obtener las compras hechas es necesario hacer una petición get a la siguiente ruta `/compras`. Si existen compras, obtenemos los siguientes resultados:

![get_compras](https://user-images.githubusercontent.com/24718808/52977074-2f372980-33cc-11e9-8c03-aa70787fdf8c.png)


#### Realizar una Compra

Para realizar una compra es necesario hacer un POST a la ruta `/compras/{id-usuario}/{id-tiquete}/cantidad` , estos parámetros deben ser números enteros, además
el servicio permite calcular el precio de la compra, resta tiquetes de la base de datos y valida si la cantidad de tiquetes a comprar
es mayor que la existente.

Si la compra se realiza con éxito nos muestra el siguiente resultado

![compra_exito](https://user-images.githubusercontent.com/24718808/52977324-23983280-33cd-11e9-88c7-b6c4353674c6.png)


Si el usuario o tiquetes no existen se muestran errores como los anteriormente mencionados cuando un tiquete o un usuario no existen en el sistema.

Si la cantidad de tiquetes que se desea comprar es mayor a la que existe, se muestra el siguiente error.

![compra_fallo](https://user-images.githubusercontent.com/24718808/52977332-27c45000-33cd-11e9-8f68-8ab7f9639b35.png)

<a name="test"></a>
## Test Aplicación 

Se va a realizar el desarrollo basado en pruebas unitarias, cada método tendrá su test unitario, se espera realizar un cubrimiento de código entre el 75 y 90 por ciento de la 
aplicación con pruebas unitarias.
Spring Framework con su manejo de Inyección de dependencias  nos ayuda a desarrollar código que sea fácil de probar. Utilizando el [Framework Junit](https://junit.org/junit5/) 
para las pruebas unitarias , además de [Mockito](https://site.mockito.org/) para manejar una sintaxis estilo BDD y realizar los Mocks, nos da herramientas para poder crear una 
aplicación bien probada.

En cuanto a las pruebas funcionales de las API Rest se utiliza la herramienta [PostMan](https://www.getpostman.com/).

<a name="hitos"></a>
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

    
    
[despliegue](https://danielbc09.github.io/Proyecto_CC/despliegue)


