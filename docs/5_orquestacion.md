# Aprovisionamiento Máquinas Virtuales Con Vagrant

Para este Hito se utiliza vagrant para orquestar dos máquinas virtuales , nuestras máquinas virtuales son , nuestro servicio 
de usuarios y su respectiva base de datos la cual va a estar desplegada e instalada en otro servidor.

Para la orquestación de estas dos máquinas se utiliza como ya se mencionó vagrant el cual como se puede ver en la [documentación](https://www.vagrantup.com/), es una herramienta para construir y gestionar entornos de máquinas virtuales con el objetivo de automatizar estas tareas.


## Selección Máquinas virtuales.

Para este hito se realizó el microservicio en nuestra confiable versión de Ubuntu 18.04-LTS,  por razones como el conocimiento de este servidor y sus comandos algo muy importante para optimizar el tiempo en despliegue con ansible.

Para la base de datos , si bien en el hito anterior se escogió un servidor de base de datos [postgres](ttps://azure.microsoft.com/es-es/pricing/details/postgresql/) ara este hito sin embargo se escoge un servidor base para despues aprovisionar con ansible la máquina virtual con la base de datos postgreSql. 

Como se puede ver en la siguiente encuesta de la [documentación](https://www.postgresql.org/community/survey/50-what-operating-system-is-your-primarylargest-production-postgresql-database-running-on/) los servidores Ubuntu es donde mas se han instalado sistemas de bases de datos PstgrSQL.

Además en el siguiente [enlace](https://redbyte.eu/en/blog/postgresql-benchmark-freebsd-centos-ubuntu-debian-opensuse/) se muestra varias pruebas  de latencia  (TPS ) para la base de datos postgres con diferentes sistemas operativos y en operaciones como lectura escritura en la base de datos y se concluye que no hay una diferencia significativa entre varias distribuciones GNU/Linux.

Por lo tanto para nuestra máquina virtual se escoge el sistema operativo Ubuntu 18.04-LTS , y como se ha dicho reiteradamente , el conocimiento que se tiene de este sistema operativo nos servirá para luego aprovisionar con ansible de una forma efectiva.

Finalmente en cuanto a la capacidad de ambas maquinas, se escoge el Basico A0, por razones de costos , el cual tine las caracteristicas que se muestran en la Imagen

![basico_a0](https://user-images.githubusercontent.com/24718808/51792589-92f67a00-21b3-11e9-9f04-1136117ca0c7.png)

## Azure y Vagrant

Existen diferentes formas de orquestar la infraestructura de una aplicación, para este hito sin embargo se utilizara vagrant como herramienta de orquestación para el PaaS azure.
Para esto se instala el el plugin de vagrant y azure que está en el siguiente [Link](https://github.com/Azure/vagrant-azure)

Para poder utilizarlo primero se realiza un login de azure:
~~~
az login
~~~
Luego se  ejecuta el comando para crear un [Azure Active Directory](https://docs.microsoft.com/es-es/azure/active-directory/develop/app-objects-and-service-principals) para poder conectarnos con el API de azure. Si se tienen varias suscripciones es recomendable primero ejecutar el comando para seleccionar la suscripción y luego se crea el AAD como sigue.

~~~
### Seleccionar Subscripción

az account set --subscription 'subscripción'

### Creación Del AAD

az ad sp create-for-rbac
~~~

Después que se crea el AAD nos muestra una serie de atributos los cuales son necesarios para la conección.

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

Además se necesita el Id de la Suscripción de Azure el cual lo podemos obtener con el siguiente comando:

~~~
az account list --query "[?isDefault].id" -o tsv
~~~

Finalmente con las variables anteriores se crea en nuestro sistema operativo las variables de entorno  que serán las credenciales con las cuales vagrant podrá conectar a azure cloud:
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

  ## Se descarga la imagen azure
  config.vm.box = 'azure'
 ## Se configura la clave privada para de nuestro equipo	
  config.ssh.private_key_path = '~/.ssh/id_rsa'

## Se define nuestra maquina virtual base de datos “database”
  config.vm.define 'database' do |database|
      
      database.vm.provider :azure do |azure, override|

	## Se configuran los parametros credenciales para que Vagrant se pueda conectar a azure
          azure.tenant_id = ENV['AZURE_TENANT_ID']
          azure.client_id = ENV['AZURE_CLIENT_ID']
          azure.client_secret = ENV['AZURE_CLIENT_SECRET']
          azure.subscription_id = ENV['AZURE_SUBSCRIPTION_ID']  
        
##Se define el grupo de la maquina virtual.
          azure.resource_group_name = 'CChito5' 
#Se define la imagen de la maquina virtual.
          azure.vm_image_urn = 'Canonical:UbuntuServer:18.04-LTS:latest'
#Se define el nombre de la maquina virtual.          
          azure.vm_name = 'posgresql'
#Se define las caracteristicas fisicas , que son las mas basicas
          azure.vm_size = 'Basic_A0'
#Se define la region donde se desplegará la maquina
          azure.location = 'francecentral'
#Se define el nombre de usuario
          azure.admin_username = 'daniel'
#Se abren los puertos , para acceder a la maquina virtual , el 5432 es el de la base de datos postgreSql por defecto.
          azure.tcp_endpoints = [22, 80, 5432]
      end 
 
# Se realiza la provision con el playbook de ansible provision_db.yml

      database.vm.provision 'ansible' do |ansible|
            ansible.playbook = 'provision_bd.yml'
      end
  end


# Se realiza la configuración de la maquina virtual servicio , donde se desplegará nuestro microservicio.

  config.vm.define 'servicio' do |servicio|

      servicio.vm.provider :azure do |azure, override|
	
#Credenciales.

            azure.tenant_id = ENV['AZURE_TENANT_ID']
            azure.client_id = ENV['AZURE_CLIENT_ID']
            azure.client_secret = ENV['AZURE_CLIENT_SECRET']
            azure.subscription_id = ENV['AZURE_SUBSCRIPTION_ID']     
      
#valores de nuestra maquina servicio igual que la anterior      
            azure.resource_group_name = 'CChito5'
            azure.vm_size = 'Basic_A0'
            azure.vm_image_urn = 'Canonical:UbuntuServer:18.04-LTS:latest'      
            azure.vm_name = 'serviciousuarios'
            azure.location = 'francecentral'
            azure.admin_username = 'daniel' 
 
	#Se abren los puertos 22 y 80 para poder acceder a la Maquina.

            azure.tcp_endpoints=[22, 80]
      end
        
	#Se aprovisiona la maquina con el playbook provision.yml
      servicio.vm.provision 'ansible' do |ansible|
          ansible.playbook = 'provision.yml'
      end
  end

end

~~~

Para las provisiones de las máqunas virtuales se utilizó ansible, para el servidor el [archivo](https://github.com/danielbc09/Proyecto_CC/blob/master/orquestacion/provision.yml) , y para la base de datos se creo un nuevo playbook con lo necesario
para instalar [postgresSql](https://github.com/danielbc09/Proyecto_CC/blob/master/orquestacion/provision_bd.yml)

### Ejecución Vagrant y orquestación Máquinas virtuales

Ya con el VagrantFile creado se ejecuta el siguiente comando:

~~~
vagrant up

#Para que la creación sea secuencial.

vagrant up --no-parallel 

~~~

Como podemos ver en la siguiente imagen , se puede ver la creacion de las dos máquinas virtuales.


![vagrant_evidencia](https://user-images.githubusercontent.com/24718808/51792724-b7535600-21b5-11e9-9bc9-e7bd9de60326.png)

## Despliegue

Finalmente se despliega la aplicación en el servidor, creando las variables de entorno de configuración para la conección de la base de datos 

~~~
JDBC_DATABASE_URL=direccion bd
JDBC_DATABASE_USERNAME=ususario
DBC_DATABASE_PASSWORD=clave cualquiera
~~~
y se ejecuta el comando:
~~~
mvn spring-boot:run
~~~
Como se puede mostrar en la siguiente imagen la máquina virtual del servidor , responde exitosamente

![prueba_exito](https://user-images.githubusercontent.com/24718808/51792699-46ac3980-21b5-11e9-9993-c385f21a82d2.png)

Además para probar la coneccion a la base de datos se utilizó la herramienta [DBeaver](), como se muestra en la siguiente imagen se configuran los parámetros de nuestra base de datos dirección Ip, puerto, Usuario y contraseña

![dbeaver_conection](https://user-images.githubusercontent.com/24718808/51792750-42345080-21b6-11e9-8c79-5fab8dd6a7e3.png)

Y nuestra conexión es un éxito como podemos ver en la siguiente imagen , está nuestra base de datos “ccproject”con las tablas creadas por el ORM Spring automáticamente.

![dbeaver_2](https://user-images.githubusercontent.com/24718808/51792755-4fe9d600-21b6-11e9-8bb8-23ddcc045a38.png)
