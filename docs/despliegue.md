### Despliegue


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





   
