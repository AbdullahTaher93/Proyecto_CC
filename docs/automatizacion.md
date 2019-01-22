# Automatización Despliegue en la nube

Para esta entrega se utiliza el cliente de azure para crear la máquina virtual del servicio que 
se va a desplegar.

Para instalar el cliente de azure se utiliza el siguiente comando:

~~~
	$ sudo apt-get install azure-cli
~~~

## Elección Maquina Virtual

Para el desarrollo de aplicaciones Java existen diferentes servidores sencillos para utilizar como por ejemplo [JBoss](http://www.jboss.org/) , [GlassFish](https://javaee.github.io/glassfish/), [TomCat](http://tomcat.apache.org/)  u otros más especializados como Weblogic, o websphere de IBM. Se investigó cuáles eran los servidores mas populares o mas usados para el desarrollo y de la siguiente página [web](https://plumbr.io/blog/java/most-popular-java-application-servers-2017-edition) se puede ver la siguiente imagen.

![servidores_usados](https://user-images.githubusercontent.com/24718808/51416114-65d51680-1b78-11e9-88f3-03c27d502d20.png)
 
Se puede ver en la siguiente tabla los servidores y el porcentaje de mercado que tienen:

				| Servidor      | % Mercado |
				| ------------- | ----------|
				| TomCat        | 63.8 %    |
				| JBoss/WildFly | 13.8 %    |
				| Jetty 	|  9.0 %    |       
				| Glassfish	|  5.6 %    |
				| Weblogic 	|  4.5 %    | 
				| Other 	|  3.4 %    | 

Como se ve el mercado está dominado por apache TomCat , esto se debe a su facilidad de uso , sus prestaciones además que los nuevos Frameworks como Spring Boot, Jersey o Javalin y las de arquitecturas modulares (Microservicios) se prefieren varios servidores simples de una aplicación en vez de un servidor robusto y costoso.

El proyecto como se ha mencionado anteriormente se va a utilizar el framework Spring-boot , el cual ya tiene por defecto embebido el servidor TomCat. además que se recomienda en el desarrollo Web utilizar este framework por defecto, como se puede apreciar en el siguiente [Link](https://stackify.com/tomcat-vs-jetty-vs-glassfish-vs-wildfly/).



Antes de crear la máquina virtual se escoge la imagen adecuada del sistema operativo que mejor se ajuste a nuestras necesidades
y además del presupuesto que se tiene en azure para la nube.

Se utiliza el sistema operativo linux pues para java es el mejor servidor para desarrollo y despliegue de aplicaciones. 
Como pue de verse en las recomendaciones de [aquí](https://stackoverflow.com/questions/256169/best-os-for-java-development). Se selecciona la distribución Ubuntu de debian ya que es la más conocida y de simple manejo dentro de la comunidad de linux además que se ha venido trabajando en los comandos de esta distribución en el hito tres con ansible.

Se utiliza la Imagen Ubuntu Server 18.04 LTS ya que es una Long Term Support lo que le da el estatus de estabilidad y soporte 
, además es la última versión de Ubuntu Server en azure y tiene actualizaciones en cuanto a seguridad y optimización del 
sistema operativo , se puede consultar el siguiente link para más información sobre la versión [18.04](https://wiki.ubuntu.com/BionicBeaver/ReleaseNotes).

En cuanto a recursos se selecciona un básico A1 ya que en el tiempo que estoy haciendo esto solo se tiene una licencia disponible 
de 21 dólares donada por un compañero , pues las anteriores se gastaron por no tener cuidado al apagar la máquina virtual. 
Además para el servicio que se va a desplegar esta máquina es suficientemente potente.

## Selección región data center.
 
Para seleccionar la región en la cual se va a crear la máquina virtual  se utiliza la página web 
[link](https://azurespeedtest.azurewebsites.net/). En ella se muestra en orden las regiones en las cuales la latencia es menor con 
base en nuestra conexión de internet.


![latencia_regiones](https://user-images.githubusercontent.com/24718808/51096413-b507f980-17bc-11e9-8f67-d429226667fb.png)


En la imagen anterior se muestra que la región de Francia Central presenta mucha menos latencia que el oeste o sur de Reino 
unido. Lo cual nos da un indicio de la región adecuada para crear la máquina virtual.


## Selección Base de datos 

### Motor

La base de datos seleccionada para este se servicio es postgresql, las características que diferencian esta base de datos sobre otras son

-Soporte ACID completo , para saber que es acid  siguiente [link](https://es.wikipedia.org/wiki/ACID).

-Licenciamiento MIT , el cual nos permite hacer muchas cosas con esta base de datos  ya sea comerciales o no.

-Porque en el despliegue con Heroku la única Base de datos gratis fue PostgreSql.


### Características Físicas

Para esta base de datos se escogio las siguientes caracteristicas fisicas:

![costos_bd](https://user-images.githubusercontent.com/24718808/51557146-6291a180-1e7c-11e9-8d8f-533db87b2cd9.png)


Esto se debe principalmente a los factores,  como el uso que se le va a dar ya que es un microservicio simple que nos guarda los usuarios y el otro factor es el precio, pues esta base de datos es la mas economica que se encontró.


## Creación de Scripts y automatización.

Primero se inicia sesión en la aplicación con el siguiente comando.

~~~
$ az login
~~~ 

Se realiza la creación del grupo con la localización de Francia central ya que es la region con menos latencia basados en las pruebas anteriores.

~~~
az group create --name ccdaniel --location francecentra
~~~

Se crea la imagen virtual  con el siguiente comando el cual asocia la imagen creada a una ip , esto se necesitará para más adelante en la creación de la Base de datos en este mismo script.

~~~
ip=$(az vm create --resource-group ccdaniel --name usermicroservice --image Canonical:UbuntuServer:18.04-LTS:latest --generate-ssh-keys --size Basic_A0 --public-ip-address-allocation static | jq -r '.publicIpAddress')
~~~


Se abre el puerto 80 para poder acceder al servidor desde internet:

~~~
az vm open-port --resource-group ccdaniel --name usermicroservice --port 80
~~~


Se crea la base de datos PostgreSql con los requerimientos fisicos de la base de datos estan definidos  la parte --sku-name  con el parametro “B_Gen5_1” el cual cada letra significa ,  B Categoría Básica, Gen5 generación de computo   y 1 (Los cores ) para más información siga el siguiente enlace de la documentación [azure](https://docs.microsoft.com/en-us/azure/postgresql/quickstart-create-server-database-azure-cli)

El usuario y la contraseña son variables de entorno del sistema , para evitar mostrar las credenciales.

~~~
az postgres server create --resource-group ccdaniel --name postgrescc --location francecentral --admin-user $database_user --admin-password $database_pass --sku-name B_Gen5_1 --version 9.6
~~~

Finalmente se añade la regla de firewall para conectarse a la BD  la cual solo permitirá conectar máquinas con la dirección ip especificada o el rango de direcciones ip especificado.
~~~
az postgres server firewall-rule create --resource-group ccdaniel --server postgrescc --name AllowMyIP --start-ip-address $ip --end-ip-address $ip
~~~

## Automatización

Los comandos anteriores se automatizan creando el script[acopio.sh](https://github.com/danielbc09/Proyecto_CC/blob/master/acopio.sh), el cual se utiliza para crear las máquinas virtual automáticamente.

Se muestra las siguientes  imágenes con el propósito de evidenciar la creación, del servidor y la base de datos:

Base de datos:


![imagen_db](https://user-images.githubusercontent.com/24718808/51557393-14c96900-1e7d-11e9-88e1-a0e27cd13d5a.png)



Servidor Ubuntu:

![imagen_servidor_1](https://user-images.githubusercontent.com/24718808/51557400-198e1d00-1e7d-11e9-8784-a59726f4fc56.png)



## Despliegue

### Base de datos  

Ya creada nuestro servidor de base de datos , es necesario ingresar con las credenciales que se colocaron a la hora de la creación con el siguiente comando.

~~~
psql --host=postgrescc.postgres.database.azure.com --port=5432 --username=daniel@postgrescc --dbname=postgres
~~~

 Después se crea el usuario y la base de datos con el objetivo que nuestra aplicación tenga las credenciales necesarias para poderse conectar. en la siguiente imagen se muestra el proceso finalzado siguiendo las instrucciones del siguiente [tutorial](https://docs.microsoft.com/en-us/azure/postgresql/howto-create-users)


### Servidor 

Despues de creado el servidor se puede ejecutar el  playbook “provision.yml” el cual instala  la infraestructura que necesaria para que nuestra aplicación se pueda instalar y ejecutar como se vio en el [hito 3](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/aprovisionamiento.md).

![ansible_instalacion](https://user-images.githubusercontent.com/24718808/51096798-9a834f80-17bf-11e9-94fc-5b0874640be6.png)

Se ingresa  a la máquina creada por medio de la consola y se configura las variables de entorno, para que el framework sepa a qué dirección y base de datos conectarse

~~~
JDBC_DATABASE_URL=direccion bd
JDBC_DATABASE_USERNAME=ususario@basededatos
JDBC_DATABASE_PASSWORD=clave cualquiera
~~~


Finalmente se ejecuta mvn para instalar nuestro microservicio para luego que se conecte a nuestra base de datos de postgres.

~~~
mvn spring-boot:run 
~~~

Se realiza una prueba a el Home de la aplicación y nos muestra el resultado de OK

![home_2](https://user-images.githubusercontent.com/24718808/51557699-ebf5a380-1e7d-11e9-9450-657680e3bcf7.png)

Además para comprobar que la conexión entre base de datos y servidor fue exitosa se muestra la siguiente imagen con los usuarios que la aplicación genera en las tablas.

Selección de Base de datos:

![prueba_conexion_1](https://user-images.githubusercontent.com/24718808/51557973-9cfc3e00-1e7e-11e9-9b45-10a201c4c676.png)


Se hace un query a la Tabla usuarios donde se puede ver que han sido creados los usuarios que aparecen en la dirección "/user/"

![prueba_db_2](https://user-images.githubusercontent.com/24718808/51558061-ccab4600-1e7e-11e9-9ff2-fe89182c19b3.png)












