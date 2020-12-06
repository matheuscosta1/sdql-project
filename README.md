# Projeto Banco de Dados NoSQL

## Descrição

Este projeto tem como objetivo implementar um banco de dados NoSQL utilizando Java e gRPC.
 
## Requisitos para executar o projeto no Ubuntu

* Instalação do Docker

```
sudo apt-get update && sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common && curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - && sudo apt-key fingerprint 0EBFCD88 && sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable" && sudo apt-get update &&  sudo apt-get install docker-ce docker-ce-cli containerd.io -y && sudo
 
    usermod -aG docker $USER && sudo chown $USER /var/run/docker.sock
```

* Instalação do Docker Compose

```
    sudo curl -L "https://github.com/docker/compose/releases/download/1.26.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose &&
    sudo chmod +x /usr/local/bin/docker-compose &&
    sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
```

## Executando o projeto

A partir dos requisitos instalados, é necessário clonar dentro do raiz deste projeto, o servidor e o client:

```
  git clone https://github.com/scarpel/sdkiel.git
  
```
Após ter feito o clone, basta executar o docker compose com o seguinte comando, no raiz deste projeto:

```
    docker-compose build && docker-compose up
```

Assim será inicializado o Servidor do nosso Banco NoSQL.
