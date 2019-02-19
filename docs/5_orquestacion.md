# Aprovisionamiento Máquinas Virtuales Con Vagrant

En este Hito se utilizó vagrant para orquestar dos máquinas virtuales, una es donde se despliegan los Microservicios de la aplicación y en la otra está su respectiva base de datos Postgres.

Para la orquestación de estas dos máquinas virtuales en azure  vagrant es la herramienta ideal como se puede ver en la [documentación](https://www.vagrantup.com/), pues nos ayuda a construir y gestionar entornos de máquinas virtuales con el objetivo de automatizar su creación y despliegue.

## Selección Máquinas virtuales.

Se realizó el microservicio en la versión de Ubuntu 18.04-LT por razones como el conocimiento de este servidor y sus comandos, algo muy importante para optimizar el tiempo en despliegue con ansible.

Para la base de datos se utiliza un un servidor Ubuntu 18.04-LTS como imagen base con el objetivo de realizar el aprovisionamiento con ansible.

Como se puede ver en la siguiente encuesta de la [documentación](https://www.postgresql.org/community/survey/50-what-operating-system-is-your-primarylargest-production-postgresql-database-running-on/) de postgresql los servidores Ubuntu son los mas utilizados para el ambiente de producción 

Además en el siguiente [enlace](https://redbyte.eu/en/blog/postgresql-benchmark-freebsd-centos-ubuntu-debian-opensuse/) se muestra varias pruebas  de latencia  (TPS ) para la base de datos postgresql desplegada en diferentes sistemas operativos haciendo transacciones de lectura y  escritura en la base de datos, se concluye que no hay una diferencia significativa entre las diferentes distribuciones GNU/Linux.

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
      #Se abren los puertos , para acceder a la máquina virtual , el 5432 es el de la base de datos postgreSql por defecto.
          azure.tcp_endpoints = [22, 80, 5432]
      end 
 

# Se realiza la provisión con el playbook de ansible provision_db.yml
      database.vm.provision 'ansible' do |ansible|
            ansible.playbook = 'provision_bd.yml'
      end
  end


# Se realiza la configuración de la máquina virtual servicio , donde se desplegará nuestro microservicio.

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
y se ejecuta el comando:
~~~
mvn spring-boot:run
~~~
Como se puede ver en la siguiente imagen la máquina virtual del servidor, responde exitosamente

![prueba_exito](https://user-images.githubusercontent.com/24718808/51792699-46ac3980-21b5-11e9-9993-c385f21a82d2.png)

Además para probar la conexión a la base de datos se utilizó la herramienta [DBeaver](), como se muestra en la siguiente imagen se configuran los parámetros de nuestra base de datos dirección Ip, puerto, Usuario y contraseña

![dbeaver_conection](https://user-images.githubusercontent.com/24718808/51792750-42345080-21b6-11e9-8c79-5fab8dd6a7e3.png)

Y nuestra conexión es un éxito como podemos ver en la siguiente imagen , muestra la base de datos “ccproject”con las tablas creadas por el ORM Spring automáticamente.

![dbeaver_2](https://user-images.githubusercontent.com/24718808/51792755-4fe9d600-21b6-11e9-8bb8-23ddcc045a38.png)
