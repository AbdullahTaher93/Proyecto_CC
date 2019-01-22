#!/usr/bin/env bash
#Variables de entorno
database_user=$AZ_DATABASE_USERADMIN
database_pass=$AZ_DATABASE_PASSWORD

#logear en consola azure
az login

#crear grupo con la localizació,
az group create --name ccdaniel --location francecentral

#crear la imagen en ubuntu

echo "Creando maquina virtual"
ip=$(az vm create --resource-group ccdaniel --name usermicroservice --image Canonical:UbuntuServer:18.04-LTS:latest --generate-ssh-keys --size Basic_A0 --public-ip-address-allocation static | jq -r '.publicIpAddress')


#abrir el puerto 80
echo "Abriendo puerto 80"
az vm open-port --resource-group ccdaniel --name usermicroservice --port 80

#Creando base de datos PostgresSQL
echo "Creando Base de datos"
az postgres server create --resource-group ccdaniel --name postgrescc --location francecentral --admin-user $database_user --admin-password $database_pass --sku-name B_Gen5_1 --version 9.6


#Configurando reglas del firewall
echo "Añadiendo regla de firewall para permitir conexiones externas de solo la ip especificada"
az postgres server firewall-rule create --resource-group ccdaniel --server postgrescc --name AllowMyIP --start-ip-address $ip --end-ip-address $ip
