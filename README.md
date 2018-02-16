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

Exemplo:

public void emailSender(JavaMailSenderImpl mailSender) {

		// Plano simples heroku instalar o sendGrid no prompt verifique os dados
		// instalado : heroku config
		// JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.sendgrid.net");
		mailSender.setUsername("app88243237@heroku.com");
		mailSender.setPassword("mrnnta0a4369");
		mailSender.setPort(25);
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		mailSender.setJavaMailProperties(mailProperties);

	}	

        @Bean
	public MailSender mailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	        emailSender(mailSender);
		
		return mailSender;
	}




No pacote br.com.casadocodigo.loja.controller
  1.Na classe Pagamento controller verificar o método:

private void enviaEmailCompraProduto(Usuario usuario) {
		
		
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		//Assunto
		email.setSubject("Compra finalizada com sucesso");
		
		//Para
		//email.setTo(usuario.getEmail());
		email.setTo("eduardo27_minotauro@hotmail.com");
				
		//Texto
		email.setText("Compra aprovada com sucesso no valor de " + carrinho.getTotal());
		
		//Quem esta enviando o emil
		//email.setFrom("eduardo.r.delfino@gmail.com");
		
		email.setFrom("eduardo.delfino@zup.com.br");
		
		//Enviar
		sender.send(email);
		
		
	}
  

```

##  4 Verificando

```sh
GET  http://localhost:8080/casadocodigo/produtos/form


```

```
