# Avances Hito 5

En este hito se utilizó ansible para aprovisionar la base de datos postgresql.

Además se avanza en la creación de los Microservicios de Tiquetes y De compras de tiquetes, además se realiza una refactorización al servicio de
usuarios.


## Avances Aplicación:

Para consultar los servicios creados y su explicación por favor dirigirse a los siguientes links.

  - [Microservicio Usuarios](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#usuarios)   
  - [Microservicio Tiquetes](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#tiquetes)
  - [Microservico Compras de tiquetes](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#compra)




## Aprovisionamiento base de datos con Ansible

### Explicación Script: 

### 1. Se realiza el documento provicion_bd.yml con las siguientes tareas:
~~~

      - name: Install postgresSql
        apt: name={{item}}
        with_items:
          - postgresql
          - libpq-dev
          - python-psycopg2

~~~

En la tarea "Install PostgreSql" se instala la base de datos postgresql con los paquetes -python-psycopg2 y el -libpq-dev

### 2 Creación base de datos y ususario.

En la siguiente parte del script se definen las variables de la base de datos `ccproject` con sus respectivo usuario y contraseña.

~~~
vars:
      dbname: ccproject
      dbuser: "{{lookup('env','AZ_DATABASE_USERADMIN')}}"
      dbpassword: "{{lookup('env','AZ_DATABASE_PASSWORD')}}"
~~~

Se utilizan variables de entorno del sistema operativo para crear las credenciales de usuario y password de una forma 
segura con la instrucción de ansible `lookup('env', {variable de entorno})`. 


Para crear la base de datos se ejecutan las siguiente instrucciones.
~~~
       - name: assure database creation
        postgresql_db: name={{dbname}}

      - name: assure user has the acces to database
        postgresql_user: db={{dbname}} name={{dbuser}} password={{dbpassword}} priv=ALL
~~~
En la instrucción "assure user has the acces to databae" nos aseguramos que el usuario tenga acceso a la base de datos.


### 3 Asegurando acceso al servicio postgres

Habiendo creado la base de datos con su respectivo usuario, se procede a configurar dos valores importantes para postgresql los cuales son 
el acceso a los puertos, tambien se configura el documento pg_hba.conf para poder ingresar a la base de datos ya que postgresql es restrictivo 
en cuanto al ingreso de usuarios externos.

~~~
      
      - name: postgresql listen all ports
        lineinfile: dest=/etc/postgresql/10/main/postgresql.conf
                  regexp="^listen_addresses"
                  line="listen_addresses = '*'" state=present
      
      - name: postgresql should allow access to host
        copy:
          dest: /etc/postgresql/10/main/pg_hba.conf
          content: |
            local   all   postgres   peer
            local   all   all        peer
            host    all   all        0.0.0.0/0   md5

~~~

En la tarea `postgresql listen all ports` se configura los puertos para que postgresql escuche las peticiones desde cualquier maquina con cualquier ip.

Con la tarea `postgresql should allow access to host` se configura el acceso a los usuarios de la base de datos, ya que por defecto 
postgresql no deja conectar a ningún usuario.

### 4 Re iniciar servicio postgresql

En esta tarea se realiza la siguiente instrucción:

~~~
     - name: Stop postgreSql
        become: true  
        become_method: sudo
        service:
          name: postgresql
          state: restarted  
~~~

Se utilizó esta instrucción sin ningún éxito pues la base de datos no realiza el reinicio y nos da el siguiente error:

![error_postgresyml](https://user-images.githubusercontent.com/24718808/52016628-601af180-24e5-11e9-8e9a-7a426c41045c.png)

Se intentó cambiar al modo de comandos con ansible pero aun así no dio resultado.


### 5 Fuentes 

Direcciones que se utilizaron para crear el playbook de postgres.

(https://docs.ansible.com/ansible/latest/modules/postgresql_db_module.html)

(https://opensource.com/article/17/6/ansible-postgresql-operations)

(https://blog.2ndquadrant.com/ansible-loves-postgresql/)

(https://groups.google.com/forum/#!topic/ansible-project/hKR3otUcgu4)