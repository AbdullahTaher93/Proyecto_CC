# Aprovisionamiento Máquinas Virtuales Con Vagrant

En este Hito se utilizó vagrant para orquestar dos máquinas virtuales, una es donde se despliegan los Microservicios de la aplicación y en la otra está su respectiva base de datos Postgres.

Para la orquestación de estas dos máquinas virtuales en azure  vagrant es la herramienta ideal como se puede ver en la [documentación](https://www.vagrantup.com/), pues nos ayuda a construir y gestionar entornos de máquinas virtuales con el objetivo de automatizar su creación y despliegue.

## Selección Máquinas virtuales.

### Máquina Servicios

Se realizó el microservicio en la versión de Ubuntu 18.04-LTS, ya que ubuntu es uno servidores mas recomendado en el entorno libre y empresarial como se puede ver [aquí](https://www.colocationamerica.com/blog/best-operating-systems-for-business-and-personal-use), los servidores Ubuntu son economicamente escalables, seguros y ademas 
soportan hardware y software mas popular como se puede ver [aquí](https://techglamour.com/server-operating-system/).

Tomando otras fuentes, como la siguiente [link](https://www.ubuntupit.com/best-linux-server-distro-top-10-compared-recommendation/) se puede ver que recomiendan la versión de Debian sobre otras versiones Linux, pues según el artículo tiene caracteristicas como estabilidad, compatibilidad, facil uso e integración alta.

La version 18.04 LTS se escogé por el soporte,  ya que es un soporte a largo plazo y ha mejorado la optimización y seguridad del sistema operativo como puede verse en la documentación [oficial](https://wiki.ubuntu.com/BionicBeaver/ReleaseNotes).

Otra razón importante es  el conocimiento de este servidor sistema operativo para optimizar el tiempo de despliegue ya que se tiene conocimiento en los comandos de esta versión.


### Máquina Base De Datos

Para la base de datos se utiliza un un servidor Ubuntu 18.04-LTS como imagen base con el objetivo de realizar el aprovisionamiento con ansible.

Como se puede ver en la siguiente encuesta de la [documentación](https://www.postgresql.org/community/survey/50-what-operating-system-is-your-primarylargest-production-postgresql-database-running-on/) de postgresql los servidores Ubuntu son los mas utilizados para el ambiente de producción 

Además en el siguiente [enlace](https://redbyte.eu/en/blog/postgresql-benchmark-freebsd-centos-ubuntu-debian-opensuse/) se muestra varias pruebas  de latencia  (TPS ) para la base de datos postgresql desplegada en diferentes sistemas operativos haciendo transacciones de lectura y  escritura en la base de datos y se concluye que no hay una diferencia significativa entre las diferentes distribuciones GNU/Linux.

En cuanto a la capacidad de ambas máquinas se escoge el Básico A0, pues estas características son lo suficientemente potentes para soportar nuestros servicios adémas que  son mas económicos como se puede observar en la siguiente imagen. 

![basico_a0](https://user-images.githubusercontent.com/24718808/51792589-92f67a00-21b3-11e9-9f04-1136117ca0c7.png)

## Azure y Vagrant

Existen diferentes formas de orquestar la infraestructura de aplicaciones en azure como por ejemplo, la consola de comandos de cliente azure o la interfaz web.Para este hito sin embargo se utilizó vagrant.

Con el objetivo de utilizar vagrant junto con azure es necesario instalar el siguiente  plugin de vagrant que está en el siguiente [Link](https://github.com/Azure/vagrant-azure). Mediante  este plugin podemos acceder al API de azure con vagrant.

Para poder utilizarlo primero se realiza login en azure:

~~~
az login
~~~

A continuación se  ejecuta el comando para crear un [Azure Active Directory](https://docs.microsoft.com/es-es/azure/active-directory/develop/app-objects-and-service-principals) para poder conectarnos con el API de azure.

Si se tienen varias suscripciones es recomendable primero ejecutar el comando para seleccionar la suscripción y luego se crea el AAD como sigue.

~~~
### Seleccionar Subscripción

az account set --subscription 'subscripción'

### Creación Del AAD

az ad sp create-for-rbac
~~~

Después de haber creado el  AAD podemos ver los atributos necesarios para la conexión con azure.

~~~
### resultado cuando se crea el AAD
{
  "appId": "XXX-XXXX-XXX",
  "displayName": "xxx",
  "name": "http://XXX-XXXX-XXX",
  "password": "XXX-XXXX-XXX8",
  "tenant": "XXX-XXXX-XXXf"
}
~~~ 

Además los atributos anteriores se necesita el Id de la Suscripción de Azure el cual lo podemos obtener con el siguiente comando:

~~~
az account list --query "[?isDefault].id" -o tsv
~~~

Finalmente con las credenciales anteriores se crea en nuestro sistema operativo las variables de entorno para utilizarlas en el Vagrantfile y para que Vagrant se pueda conectar al API de azure.

~~~
export AZURE_TENANT_ID=XXXXXXXXXXXXXXXXXXXXXXXXXX
export AZURE_CLIENT_ID=XXXXXXXXXXXXXXXXXXXXXXXXXX
export AZURE_CLIENT_SECRET=XXXXXXXXXXXXXXXXXXXXXX
export AZURE_SUBSCRIPTION_ID=XXXXXXXXXXXXXXXXXXXX
~~~

### Creación Vagrant File

Se desarrolla un [Vagrantfile](https://github.com/danielbc09/Proyecto_CC/blob/master/orquestacion/Vagrantfile) con el objetivo del despliegue automático de nuestros servicios hacia la plataforma azure.

A continuación la estructura del documento con su explicación.

~~~
##Se inicia el vagrant file para configurar el servicio
Vagrant.configure('2') do |config|

  ## Se descarga la imagen azure Dummy
  config.vm.box = 'azure'
 ## Se configura la clave privada para de nuestro equipo	
  config.ssh.private_key_path = '~/.ssh/id_rsa'

## Se define nuestra maquina virtual base de datos “database”
  config.vm.define 'database' do |database|
      
      database.vm.provider :azure do |azure, override|

	## Se configuran los parámetros credenciales para que Vagrant se pueda conectar a azure
          azure.tenant_id = ENV['AZURE_TENANT_ID']
          azure.client_id = ENV['AZURE_CLIENT_ID']
          azure.client_secret = ENV['AZURE_CLIENT_SECRET']
          azure.subscription_id = ENV['AZURE_SUBSCRIPTION_ID']  
        
      #Se define el grupo de la máquina virtual.
          azure.resource_group_name = 'CChito5' 
      #Se define la imagen de la maquina virtual.
          azure.vm_image_urn = 'Canonical:UbuntuServer:18.04-LTS:latest'
      #Se define el nombre de la máquina virtual.          
          azure.vm_name = 'posgresql'
      #Se define las características físicas de las máquinas virtuales
          azure.vm_size = 'Basic_A0'
      #Se define la región donde se desplegará la maquina
          azure.location = 'francecentral'
      #Se define el nombre de usuario
          azure.admin_username = 'daniel'
      #Se abren los puertos, para acceder a la máquina virtual, el 5432 es el de la base de datos postgreSql por defecto.
          azure.tcp_endpoints = [22, 80, 5432]
      end 
 

# Se realiza la provisión con el playbook de ansible provision_db.yml
      database.vm.provision 'ansible' do |ansible|
            ansible.playbook = 'provision_bd.yml'
      end
  end


# Se realiza la configuración de la máquina virtual servicio, donde se desplegará nuestro microservicio.

  config.vm.define 'servicio' do |servicio|

      servicio.vm.provider :azure do |azure, override|
	
            #Se configuran los parámetros credenciales para que Vagrant se pueda conectar a azure
            azure.tenant_id = ENV['AZURE_TENANT_ID']
            azure.client_id = ENV['AZURE_CLIENT_ID']
            azure.client_secret = ENV['AZURE_CLIENT_SECRET']
            azure.subscription_id = ENV['AZURE_SUBSCRIPTION_ID']     
      
            #valores de nuestra máquina servicio igual que la anterior 

            #Se define el grupo de la máquina virtual
            azure.resource_group_name = 'CChito5'
            #Se define el tamaño de los discos de la maquina virtual.
            azure.vm_size = 'Basic_A0'
            #Se define la maquina virtual.
            azure.vm_image_urn = 'Canonical:UbuntuServer:18.04-LTS:latest'      
            #Se define el nombre de la maquina virtual.
            azure.vm_name = 'serviciousuarios'
            #Se define la localización.
            azure.location = 'francecentral'
            #se Crea  el administrador daniel de la maquina
            azure.admin_username = 'daniel' 
 
	#Se abren los puertos 22 y 80 para poder acceder a la Máquina de virtual
            azure.tcp_endpoints=[22, 80]
      end
        
	##Se aprovisiona la máquina con el playbook provisión.yml el cual instala todo lo necesario para crear la base de datos
      servicio.vm.provision 'ansible' do |ansible|
          ansible.playbook = 'provision.yml'
      end
  end

end

~~~

Para las provisiones de las máqunas virtuales se utilizó ansible, la provición del servicio se encuentra en siguiente [archivo](https://github.com/danielbc09/Proyecto_CC/blob/master/orquestacion/provision.yml).Para la base de datos se creo un nuevo playbook con lo necesario
para instalar [postgresql](https://github.com/danielbc09/Proyecto_CC/blob/master/orquestacion/provision_bd.yml)

### Ejecución Vagrant y orquestación Máquinas virtuales

Ya con el VagrantFile creado se ejecuta el siguiente comando:

~~~
vagrant up

#Para que la creación sea secuencial.

vagrant up --no-parallel 

~~~

Como podemos ver en la siguiente imagen, se puede ver la creación de las dos máquinas virtuales.


![vagrant_evidencia](https://user-images.githubusercontent.com/24718808/51792724-b7535600-21b5-11e9-9bc9-e7bd9de60326.png)

## Despliegue

Finalmente se despliega la aplicación en el servidor, creando las variables de entorno de configuración para la conexión de la base de datos 

~~~
JDBC_DATABASE_URL=direccion bd
JDBC_DATABASE_USERNAME=ususario
DBC_DATABASE_PASSWORD=clave cualquiera
~~~
se ejecuta el comando:

~~~
sudo mvn spring-boot:run
~~~

## Evidencias 

Como se puede ver en la siguiente imagen la máquina virtual del servidor responde exitosamente

![evidencia_hito5_home](https://user-images.githubusercontent.com/24718808/53003693-d39e8780-342f-11e9-868c-fe4d074f738f.png)


### Microservicio Usuarios

Para el microservicio de usuarios se realiza la prueba de obtener usuarios y crear usuarios, para más información sobre este microservicio
diríjase al siguiente [link](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#usuarios)

Al dirigirse  a la dirección `http://40.89.155.201/user` se pueden ver los usuarios.

![get_users](https://user-images.githubusercontent.com/24718808/53004341-43614200-3431-11e9-857d-c22eaf295b53.png)

Con la herramienta Postman se realiza un post para crear el usuario.

![creacion_usuario_nube](https://user-images.githubusercontent.com/24718808/53005180-fda57900-3432-11e9-9c68-d57b129d50f9.png)

### Microservicio Tiquetes

Para ver el funcionamiento de este microservicio diríjase a la [documentación](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#tiquetes)

Se realiza un get para obtener los tiquetes

![get_compras](https://user-images.githubusercontent.com/24718808/53004909-68a28000-3432-11e9-97e5-ae85a97307b8.png)


### Microservicio Compras

Para ver el funcionamiento de este microservicio diríjase a la [documentación](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#compra)

Se realiza una petición POST para realizar una compra válida:

![compras_1](https://user-images.githubusercontent.com/24718808/53005378-6e4c9580-3433-11e9-8cf2-96fa5a901f8d.png)

Se obtienen las compras realizadas

![compras_get](https://user-images.githubusercontent.com/24718808/53005697-0c406000-3434-11e9-8337-e091bb7a08c3.png)

Para probar la conexión a la base de datos se utilizó la herramienta [DBeaver](https://dbeaver.io/) como se muestra en la siguiente imagen, se configuran los parámetros de nuestra base de datos como la dirección Ip, puerto, Usuario y contraseña.

![prueba_dveaver_1](https://user-images.githubusercontent.com/24718808/53005803-41e54900-3434-11e9-8ff2-ef1da80e80f9.png)


Finalmente la siguiente imagen muestra las tablas creadas en la base de datos.

![dbeaver_2](https://user-images.githubusercontent.com/24718808/53006074-d0f26100-3434-11e9-88a1-1a58daf80783.png)

Para mas información sobre como se realiza la conexión a la base de datos diríjase al siguiente [link](https://github.com/danielbc09/Proyecto_CC/blob/master/docs/Documentacion.md#base-datos). 


## Referencias

- http://jj.github.io/CC/documentos/temas/Orquestacion
- http://www.vagrantbox.es/
- https://askubuntu.com/questions/465454/problem-with-the-installation-of-virtualbox
- https://stackoverflow.com/questions/30075461/how-do-i-add-my-own-public-key-to-vagrant-vm
- https://blog.scottlowe.org/2017/12/11/using-vagrant-with-azure/
- https://opensource.com/article/17/6/ansible-postgresql-operations
- https://stackoverflow.com/questions/256169/best-os-for-java-development
- https://www.colocationamerica.com/blog/best-operating-systems-for-business-and-personal-use
- https://www.ubuntupit.com/best-linux-server-distro-top-10-compared-recommendation/