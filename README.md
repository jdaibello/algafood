# Algafood

## algafood-api

Para rodar o projeto e enviar ao Docker, seguem os seguintes comandos:

* ```sudo mvn package -DskipTests```
* ```docker image build -t algafood-api .```
* ```docker-compose down --volumes```
* ```docker-compose up --scale algafood-api=2```