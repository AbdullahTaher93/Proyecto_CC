# Comprobación orquestación Miguel goncalves (@migueldgoncalves)

Para comprobar la maquia virtual del compañero  Miguel primero se clona el repositorio con todos los archivos.

La arquitectura del compañero Miguel consisete en dos Servicios que se comunican entre si,  un servicio de información y otro de viajes ,
el despliegue esta totalmente atomatizado , y solo es necesario ejecutar los comandos de vagrant para poder desplegar
la aplicación

## Pasos para el despliegue
 
 
1. Se ejecuta el comando de vagrant `vagrant up info` el cual realiza el despliegue de la primera maquina virtual con el
servicio info.

La siguiete imagen muestra el servicio siendo creado.

![paso_1](https://user-images.githubusercontent.com/24718808/52013529-92c0ec00-24dd-11e9-8daf-d63100081704.png)

2. Se ejecuta el comando `vagrant up viajes` el cual despliega el otro servicio , ademas ena configuración del script esta
el proceso de provisionamiento y despliegue de los archvios ansible.yml. 
En la siguiente imagen se muestra el resultado de la provision de los servicios viajes e info.

![comprovacion_provision](https://user-images.githubusercontent.com/24718808/52013677-06fb8f80-24de-11e9-812a-26ebca4aa95a.png)


## Prueba funcional servicios

Para la prueba funcional de nuestros servicios se ejecuta el navegador donde esta el servicio información:

![azure_infos](https://user-images.githubusercontent.com/24718808/52014659-6c508000-24e0-11e9-8086-0fce1c73b6eb.png)

se realiza la prueba del servicio viajes como se muestra en la siguiente pantalla:

![servicio_viajes](https://user-images.githubusercontent.com/24718808/52014862-eb45b880-24e0-11e9-9287-01e8029bf357.png)

Ademas como el servicio de información se comunica con el de viajes , se comprueba la dirección `viajes/2` para comprobar 
su comportamiento.

![servicio_informacion](https://user-images.githubusercontent.com/24718808/52014975-319b1780-24e1-11e9-9e23-9dbf2e8f1163.png)