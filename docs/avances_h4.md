# Avances Hito 4.

Para este hito se realizaron dos 3 avances principales los cuales son , creación de un servicio de login mediante Token, Incorporación de la base de datos y mejoras con respecto a las respuestas de error.

## Autenticación y Autorización Utilizando JWT

La seguridad es fundamental cuando se exponen las API’s a internet de nuestra aplicación  ya que cualquier vulnerabilidad puede afectar nuestro sistema llevando a grandes problemas como pérdida de dinero , desconfianza por parte de los usuarios , robo de datos etc.

La seguridad es inherente a cualquier aplicación que se planee desarrollar, por lo tanto como desarrolladores se debe propender a aplicar las prácticas de desarrollo seguro , para reducir a lo más mínimo las vulnerabilidades en nuestro código , una de las principales estándares o prácticas que existen hoy para asegurar APIs es el protocolo [Oauth2](https://oauth.net/2/), sin embargo debido a su complegidad no se va aplicar en este proyecto , se utilizaran son los JWT (Json WEB TOken) el cual es un metodo muy comun tambien  para autenticar y autorizar a los usuarios.

### JSON Web Token(JWT)

Los [JWT](https://jwt.io/) son un estándar para la creación de token de acceso con el objetivo de autenticar y autorizar a un usuario o cliente  a que acceda a un recurso determinado de nuestro sistema.

Generalmente el token utiliza algoritmos de cifrado como HMAC con SHA-256, el cual va en en los Headers de las peticiones Http , con lo cual el servidor podrá comprobar si el token es válido, si se comprueba que es válido el recurso además comprobará que el usuario que envía el token tenga los permisos necesarios para acceder al recurso , Este proceso se conoce como Autenticación  Identificación del usuario y Autorización validar los permisos de usuario para el acceso a un recurso determinado.

Además de ser un estándar muy usado los JWT están reemplazando a los mecanismos de sesión ya que al ser Stateless , el servidor tiene una menor sobrecarga al manejar sesiones entre otras.







### Spring Security

El framework spring-boot ofrece un módulo con el cual se “facilita” la implementación de seguridad en el servidor [Spring Security](https://spring.io/projects/spring-security), ademas se va autilizar la libreria  [Java JWT: JSON Web Token](https://github.com/jwtk/jjwt), la cual nos ayuda al manejo de los JWT con Java.

La implementación sigue un flujo como se muestra en la siguiente imagen

![untitled diagram 3](https://user-images.githubusercontent.com/24718808/51558664-50196700-1e80-11e9-8157-99668d91320d.jpg)

Lo primero es que el usuario envía una petición con sus credenciales usuario y contraseña , el servidor valida si son correctas y responde con un JWT, finalmente el usuario recibe el token y con él nuevamente hace una petición al recurso que quiere ya sea una imagen, un archivo o un método  de la API. El servidor comprueba si el usuario está autorizado para acceder a ese recurso , en caso de que esté autorizado el servidor responde con el recurso y un STATUS 200, si no está autorizado responde con un codigo de error 403

### Configuración y desarrollo

Los pasos principales para usar e implementar los JWT con spring-boot son:

Se añade las dependencias en el maven Spring-Security y la Librería JWT en el archivo [POM](https://github.com/danielbc09/Proyecto_CC/blob/master/pom.xml)

~~~
       <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
    <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.8.0</version>
        </dependency>
~~~


Y se crea una clase de configuración  llamada [SecurityConfig.java](https://raw.githubusercontent.com/danielbc09/Proyecto_CC/master/src/main/java/cc/project/busapp/configuration/SecurityConfig.java) la cual es la que va a Verificar que el Token se valido y permitir que los usuarios con Un rol definido puedan acceder al recurso específico.


En el siguiente extracto de código se muestra la configuración principal

~~~               
  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/auth/signin").permitAll()
                    .antMatchers(HttpMethod.GET, "/user/**").permitAll()
                    .antMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

~~~
Como se puede ver se aseguró el método de borrar un usuario , para que sólo acceden usuarios con el ROL Administrador.

### Pruebas API REST

Se utiliza la aplicación Postman , para la nueva funcion de autenticación es necesario enviar una petision JSON(aplication/json) con los parametros siguientes:

~~~
{
	"userName": "admin",
	"password": "1234"
}
~~~

Si se configura correctamente los parametros nos muestra una respuesta como esta , donde vemos el Token Generado:


![auth_valido](https://user-images.githubusercontent.com/24718808/51559083-4b08e780-1e81-11e9-844b-d52921e33734.png)

#

Si no se colocan bien las credenciales la aplicación nos muestra el siguiente error:

![auth_no_valida](https://user-images.githubusercontent.com/24718808/51559095-4e03d800-1e81-11e9-9628-482b17f5ee65.png)

#

Para eliminar un usuario esnecesario estar autenticadoc on el Token y ser administrador de lo contrario no se podra Borrar un usuario.

EndPont
~~~
user/2
~~~

En la siguiente imagen se muestra la respuesta que se da a un usuario no autenticado , responderá con error:

![delete_forbiden](https://user-images.githubusercontent.com/24718808/51559759-14cc6780-1e83-11e9-9c41-0321d808d75b.png)

#

Sin embargo si el usuario ya esta autenticado y es administrador, se configura el header con el token para que pueda borrar el usuario.
![delete_exitoso](https://user-images.githubusercontent.com/24718808/51559844-54934f00-1e83-11e9-9870-340da624fba4.png)




