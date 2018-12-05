# Provisionamiento maquina virtual compañero  Miguel de Oliveira Dias Gonçalves

Se realiza la comprobación de el despliegue de la maquina virtual de [Miguel Gonçalves](https://github.com/migueldgoncalves).


Para realizar la comprobación se crea una maquina virtual Ubuntu 16.04 t en azure.Standard D2s v3
con los puertos 22 y 80 abiertos.

Se configura el fichero host , colocando la ip de la nueva maquina virtual.

~~~
[comprobacion]
40.121.164.101 ansible_user=danielbc
~~~

se ejecuta el comando ansible comprobacion -m ping
~~~
40.121.164.101 | SUCCESS => {
    "changed": false, 
    "ping": "pong"
}
~~~

Despues de descargar el [fichero](https://github.com/migueldgoncalves/CCproj_1819/blob/master/provision/hito3.yml) se ejecuta
el playbook hito3.yml con el siguiente comando.

~~~
ansible-playbook hito3.yml 
~~~

Despues de que se ejecuta la tarea "running application using maven" podemos ver en la dirección http://40.121.164.101/ la 
siguiente respuesta.

![prueba](https://user-images.githubusercontent.com/24718808/49535865-7c275b80-f8c5-11e8-910b-b1729f9689f2.png)



Luego siguiendo las [instrucciones](https://github.com/migueldgoncalves/CCproj_1819/blob/master/docs/provisionamiento.md) que estan en el repositorio , 
tecleamos crt-c para parar el proceso de despliegue y obtenemos la siguiente pantalla.

![end_comprobacion](https://user-images.githubusercontent.com/24718808/49535901-9103ef00-f8c5-11e8-81ae-e3dbccd90262.png)




 





