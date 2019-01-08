# Aprovisionamiento de Máquinas Virtuales 

## Creación Maquina virtual Azure:

En esta fase del proyecto se decide desplegar en [Microsoft Azure](https://azure.microsoft.com) , ya que es una plataforma en la nube más importante que lidera la competencia actual junto con google y amazon web services.

Se escoge como máquina virtual el sistema  Ubuntu Server 18.04 LTS del tamaño Standar D2sv3  la cual tiene espacio suficiente para desplegar nuestra aplicación hecha en el anterior servicio.
Se ha creado una maquina virtual en azure Ubuntu Server 18.04 LTS. En el [fichero](https://github.com/danielbc09/Proyecto_CC/blob/master/provision/provision.yml) esta la configuracin necesaria para   
 esta la configuración necesaria para la instalación de la infraestructura necesaria para nuestra aplicación , asi mismo como el despliegue.
Se configura para que estén abiertos los puertos 80 y 22 y que la conexión sea mediante ssh configurando la clave pública.

![img_1](https://user-images.githubusercontent.com/24718808/49506737-1dd78a00-f87f-11e8-8c3f-22c46490be79.png)

## Aprovisionamiento con ansible

Se realiza el aprovisionamiento con la herramienta Ansible , la cual se escoge por lo fácil de manejar y su practicidad a la hora de trabajar con los playbooks.

Se configura  el documento host para que nos podamos conectar a la máquina virtual. Se ejecuta el comando ansible azure -m ping y nos responde 

![img_2](https://user-images.githubusercontent.com/24718808/49506766-2d56d300-f87f-11e8-8d0e-39d194a22487.png)


## Playbook

Para la instalación de las herramientas necesarias para desplegar nuestra aplicación es necesario definirlas en un [Playbook](https://docs.ansible.com/ansible/2.5/user_guide/playbooks.html) de ansible el cual es donde se van a realizar todas las tareas de instalación , este playbook está en formato YAML.

la siguientes son las tareas que se realizan en el archivo provision.yml el cual es el que realizará las actividades de aprovisionamiento de la máquina virtual.

~~~
-  hosts: azure
   become: yes
~~~
 - En hosts , se indica la maquina virtual configurada en el archivo "hosts" de ansible , la cual es la maquina virtual creada.
 - become se coloca para indicar si es necesario que se ejecute una tarea como root de la maquina.
 
 Luego las tareas son las siguientes :
 
 ~~~
 - name: Ensure apt cache is up to date
   apt: update_cache=yes
 ~~~
 - En apt update cache , verificamos que el cache este actualizado.
 
~~~
    - name: Install add-apt-repostory
      apt: name=software-properties-common state=latest
~~~
- en esta tarea se mira que el repositorio software-properties-common este en su ultima version

### Java

En el siguiente snipet de codigo se instala java 8
~~~
      - name: Add Oracle Java Repository
        apt_repository: repo='ppa:webupd8team/java'

      - name: Accept Java 8 License
        debconf: name='oracle-java8-installer' question='shared/accepted-oracle-license-v1-1' value='true' vtype='select'

      - name: Install Oracle Java 8
        apt: name={{item}} state=latest
        with_items:
          - oracle-java8-installer
          - ca-certificates
          - oracle-java8-set-default
~~~
- Se añade el repositorio "ppa:webupd8team/java"  de java8
- En debconf se configura para que instale oracle java 8 y que acepte los terminos de licenciade oracle.
- Finalmente se instala los paquetes de java 8. 

### Git

Se instala git de la siguiente forma
~~~
      - name: Install Git
        apt:
          name: git
          state: present
~~~
- el anterior snippet comprueba que esté instalado git, si no esta instalado lo instala.

### Maven

 
Se instala maven de la siguiente forma
~~~
      - name: Install Maven
        apt:
          name: maven
          state: present
~~~
-El anterior snippet comprueba que esté instalado maven, si no esta instalado lo instala. 

 ###ClonarRepositorio
 
 Se clona el repositorio de git hub 
 ~~~
- name: Clone Git project
        git:
          dest: Documents/CCProject
          repo: https://github.com/danielbc09/Proyecto_CC.git
 ~~~
 ### Cambio de puertos
 
 Para que la aplicación pueda funcionar correctamente, se tiene que direccionar el puerto 80 a el 8080 que es donde SpringBoot se despliega por defecto
 se despliega (fuente) [https://askubuntu.com/questions/444729/redirect-port-80-to-8080-and-make-it-work-on-local-machine].
 
 ~~~
       - name: Redirect ports
           become: true
           become_method: sudo
           command: iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080
 ~~~

 ### Despliegue aplicación
 
 Finalmente para desplegar la aplicación en maven se raliza el siguiente comando.
 
  ~~~
       - name: Run application using Maven
         shell: nohup mvn spring-boot:run &
         args:
           chdir: Documents/CCProject
  ~~~
   -Se ejecuta un comando shell que realiza la comprobación de test y elinicio de la aplicación en Springboot, el comando nohup se utiliza 
   que el proceso con ansible continue y no se quede estancado.

### Instalación de infraestructura Ansible
 
Se ejecuta el playbook con el siguiente comando para que instale toda la infraestructura necesaria para nuestra aplicación:

~~~
ansible-playbook provision.yml -v
~~~

La siguente imagen muestra el resultado de la instalación de la infraestructura en la maquina virtual , como se puede observar 
el resultado es exitoso para todos los pasos.

![img_4](https://user-images.githubusercontent.com/24718808/49506790-3b0c5880-f87f-11e8-945d-810afc7761d9.png)

Para probar que la maquina esté funcionando se ejecuta el comando curl y obtenemos la siguiente respuesta.

~~~
curl http://168.62.51.36
{"status":"OK","ejemplo":{"ruta":"/user/1","valor":{"userId":1,"name":"Jhon Doe","userName":"jhonDoe","email":"jhonDoe@mail.com"}}}
~~~

 
Es posible acceder a la [aplicación](http://168.62.51.36) por medio del navegador navegador y ver la siguiente imagen.

~~~
http://168.62.51.36/
~~~

![img_5](https://user-images.githubusercontent.com/24718808/49506787-38a9fe80-f87f-11e8-8041-ccabe47ba3d1.png)
 
 
### Comprobación maquina virtual compañero.

Se realiza la comprobación de la máquina virtual para el compañero Miguel Gonçalves, el cual esta en el siguiente [enlace](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/prov_%20migueldgoncalves.md)

 Mi compañero Miguel también comprobo mi maquina virtual esta en el siguiente [enlace](https://github.com/migueldgoncalves/CCproj_1819/blob/master/docs/comprobacion.md)

 
