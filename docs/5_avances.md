# Avances Hito 5

Para este hito se decide realizar un documento de provición para la base de datos postgreSql, para poder instalar y crear 
una base de datos y el usuario autorizado y que nuestro servicio de usuarios se pueda conectar con la base de datos.


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

En la tarea "Install PostgreSql" se instala la base de datos postgres con los paquetes -python-psycopg2 y el -libpq-dev

### 2 Creación base de datos y ususario.

En la siguiente parte del script se definen las variables de la base de datos `ccproject` con sus respectivos usuarios

~~~
vars:
      dbname: ccproject
      dbuser: "{{lookup('env','AZ_DATABASE_USERADMIN')}}"
      dbpassword: "{{lookup('env','AZ_DATABASE_PASSWORD')}}"
~~~

se utilizan variables de entorno del sistema operativo para generar estas credenciales  de usauriop y password de una forma 
segura con la instrucción de ansible `lookup('env', {variable de entorno})`. 


Para crear la base de datos se ejecutan los siguiente instrucciones.
~~~
       - name: assure database creation
        postgresql_db: name={{dbname}}

      - name: assure user has the acces to database
        postgresql_user: db={{dbname}} name={{dbuser}} password={{dbpassword}} priv=ALL
~~~
ademas en la instrucción "assure user has the acces to databae" nos aseguramos y le damos permiso a el usuario que tenga 
acceso a la base de datos con su respectivo password.


### 3 Asegurando acceso al servicio postgres

Ya habiendo creado la base de datos con su usuario respectivo , se procede a configurar dos valores inportantes para postgreSql
que son el acceso a los puertos y tambien se configura el documento pg_hba.conf para poder ingresar a la base de datos.
Ya que postgres es restrictivo en cuanto la instalación y configuraciones por defecto.

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

En la tarea postgresql listen all ports , se configura para que postgres escuche las peticiones desde cualquier maquina con cualquier ip.

La tarea postgresql should allow access to host se configura el acceso a los usuarios de la base de datos , ya que por defecto 
postgrs no deja conectar a ningún usuario así ya estuviera creado.

### 4 Re iniciar servicio postgresql


Para esta tarea se realiza el siguiente instrucción:

~~~
     - name: Stop postgreSql
        become: true  
        become_method: sudo
        service:
          name: postgresql
          state: restarted  
~~~

Se utilizo esta instrucción sin ningun exit, pues la base de datos no realiza el reinicio y no da el siguiente error:

![error_postgresyml](https://user-images.githubusercontent.com/24718808/52016628-601af180-24e5-11e9-8e9a-7a426c41045c.png)

ademas dado el tiempo buscando y cambiando de instrucciones por ejemplo con stop , o enviar el comando directamente con sudo ,
se tuvo que dejar para una posterior entrega el arreglo de este error.

### 5 Fuentes 

Firecciones que se utilizaron para crear el playbook de postgres.

(https://docs.ansible.com/ansible/latest/modules/postgresql_db_module.html)

(https://opensource.com/article/17/6/ansible-postgresql-operations)

(https://blog.2ndquadrant.com/ansible-loves-postgresql/)

(https://groups.google.com/forum/#!topic/ansible-project/hKR3otUcgu4)