#logear en consola azure
az login

#crear grupo con la localizació,
az group create --name ccdaniel --location francecentral

#crear la imagen en ubuntu
az vm create --resource-group ccdaniel --name usermicroser --image Canonical:UbuntuServer:18.04-LTS:latest --generate-ssh-keys --size Basic_A0

#abrir el puerto 80
az vm open-port --resource-group ccdaniel --name usermicroser --port 80
