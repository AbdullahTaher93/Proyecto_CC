# Automatización Dezpliegue en la nube

Para esta entrega se utiliza el cliente de azure para crear la máquina virtual del servicio que 
se va a desplegar.

Para instalar el cliente de azure se utiliza el siguiente comando:

~~~
	$ sudo apt-get install azure-cli
~~~

## Elección Maquina Virtual

Antes de crear la máquina virtual se escoge la imagen adecuada del sistema operativo que mejor se ajuste a nuestras necesidades
y además del presupuesto que se tiene en azure para la nube.

Se utiliza el sistema operativo linux pues para java es el mejor servidor para desarrollo y despliegue de aplicaciones. 
Como pue de verse en las recomendaciones de [aquí](https://stackoverflow.com/questions/256169/best-os-for-java-development). Se selecciona la distribución Ubuntu de debian ya que es la más conocida y de simple manejo dentro de la comunidad de linux además que se ha venido trabajando en los comandos de esta distribución en el hito tres con ansible.

Se utiliza la Imagen Ubuntu Server 18.04 LTS ya que es una Long Term Support lo que le da el estatus de estabilidad y soporte 
, además es la última versión de Ubuntu Server en azure y tiene actualizaciones en cuanto a seguridad y optimización del 
sistema operativo , se puede consultar el siguiente link para más información sobre la versión 18.04(https://wiki.ubuntu.com/BionicBeaver/ReleaseNotes).

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

## Creación de Scripts y automatización.

Primero se inicia sesión en la aplicación con el siguiente comando.

~~~
$ az login
~~~ 

Se realiza la creación del grupo con la localización de Francia central ya que es la region con menos latencia basados en las
pruebas anteriores.

~~~
$ az group create --name hito4 --location francecentral
~~~

Se crea la imagen virtual  con el siguiente comando.

~~~
$ az vm create --resource-group {nombre del recurso} --name userservice --image {imagen seleccionada } --generate-ssh-keys --size {el tamaño de la máquina virtual}
~~~

Finalmente se abre el puerto 80 para poder acceder a el desde internet:

~~~
az vm open-port --resource-group {mi recurso} --name {nombre de la imagen} --port 80
~~~

Con los comandos anteriores se puede ejecutar el  playbook “provision.yml” el cual instala  la infraestructura que necesaria para 
que nuestra aplicación se pueda instalar y ejecutar.

Los comandos anteriores se automatizan creando el script[acopio.sh](https://github.com/danielbc09/Proyecto_CC/blob/master/acopio.sh), 
el cual se utiliza para crear la máquina virtual automáticamente.

Luego de ejecutar el script "acopio.sh" , nos deberia aparecer una imagen como la siguiente en la consola local.

![imgenacoplo](https://user-images.githubusercontent.com/24718808/51096773-5ee88580-17bf-11e9-8b09-73de1ac10d65.png)

Se muestra la siguiente  imagen con el proposito de evidenciar la creación, de la maquina virtual de una forma mas clara.


![imagen_vm_creada](https://user-images.githubusercontent.com/24718808/51096406-a28dc000-17bc-11e9-8942-e1d5a45bd936.png)


Con la máquina creada se procede a ejecutar con ansible la infraestructura que el servicio necesita.

![ansible_instalacion](https://user-images.githubusercontent.com/24718808/51096798-9a834f80-17bf-11e9-94fc-5b0874640be6.png)


Finalmente se instala la aplicación y se prueba el servicio como muestra la siguiente imagen.

![prueba_final](https://user-images.githubusercontent.com/24718808/51096843-f352e800-17bf-11e9-97c1-45b3f9dfecba.png)






