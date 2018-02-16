# casadocodigo
# scop-session

```sh
Exemplo carrinho de compras
como criar sessão e request map

```

## 1. Requisitos 

Instalar as seguintes ferramentas:

    1. JDK 1.8
    3. Maven

    Obs: esse projeto esta utilizando o banco postgres, porém pode ser realizado alterações na pasta conf/JPAConfiguration.java para o banco desejado

    Opcional: caso você utilize banco de dados não e necessário instalar os itens abaixo.

    4. docker

    Aqui no dbeaver você podera adcionar o banco postgres ou o  banco desejado para visualizar os dados se estiver utilizando o docker-compose
    caso deseja utilizar outro banco no docker e necessario baixar a imagem do banco desejado pois aqui esta a imagem do postgres 9.4
    5  dbeaver

##  2. Executar
```sh
Limpa o projeto:
mvn clean 

Istala o jar ou war
mvn clean install

Ob: caso esteja utilizando o docker: execute esse comando no prompt dentro da pasta do projeto.
docker-compose up

Agora e so iniciar o projeto por uma IDE ou execute o comando no prompt dentro da pasta do projeto.
mvn spring-boot:run

```

##  3 Verificando

```sh
Exemplos de Envio de email

No pacote br.com.casadocodigo.loja.conf

Abra a classse AppWebConfiguration : e acesse os métodos de envio de email

1. Gmail
2. heroku : sendgrid
3. Para testar heroku: mailtrap

```

##  4 Verificando

```sh
GET  http://localhost:8080/casadocodigo/produtos/form


```

```
