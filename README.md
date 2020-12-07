
# Labsit Banking API
Challenge proposed by the LabSit's recruit team during a Java developer job interview.

# Candidato
Eduardo Fillipe Fillipe da Silva Reis - https://www.linkedin.com/in/eduardo-fillipe-silva-reis/

# Informações importantes
- ## Acesso ao projeto
	A API foi hospedada numa instância AWS com acesso à porta 8080. E está acessível através dos endpoints:
	- API: http://54.233.210.237:8080/labSitBanking/api/v1/
	- SWAGGER: http://54.233.210.237:8080/labSitBanking/api/v1/swagger-ui.html

**Após o fim do processo seletivo o a instância acima será encerrada e este repositório marcado como privado.**

- ## Tecnologias Utilizadas
	- Java
		- Versão 8
	- Ecossistema Spring
		- Spring WEB MVC
		- Spring Core
		- Spring Data JPA
	- Banco de dados
		- H2
	- Swagger
		- Gerado automaticamente
- Cloud
	- AWS
# Considerações adicionais

O desenvolvimento do projeto seguiu os requisitos funcionais e não funcionais indicados no documento enviado pela equipe técnica da Labsit. Entretanto, alguns requisitos importantes não foram estabelecids, como os requisitos de Segurança. Dessa forma a estrutura base do projeto foi desenvolvida para suportar as características abaixo, entretanto as mesmas não foram implementadas mas podem ser seguindo a especificação OAuth2.

 - ## Segurança
	- Recomenda-se que o endpoint da API esteja seguro através do protocolo HTTPS.
	- Recomenda-se a implementação de um Sistema de segurança Oauth2 para gerenciar autenticações e autorizações, de preferência externo ao servidor da API Banking.
	- Recomenda-se que a API Banking não seja exposta diretamente, mas através de um Middleware de Integração como a CA API Gateway ou IBM Integration BUS que irá expor serviços ao usuário criando abstrações e contratos conforme demanda.

 - **Sugestão de arquitetura de segurança**:
	 - **Autenticação**	![Authentication Pipeline](https://github.com/eduardo-fillipe/labsit-banking-api/blob/main/readme-images/auth.png?raw=true)
	
		
	 1. Requisita autenticação enviando as credenciais de usuário e senha
	 2. Requisição é interceptada pelo Middleware do sistema
	 3. A requisição de autenticação é repassada para o sistema de Segurança
	 4. Servidor de Segurança requisita informações complementares
	 5. Middleware solicita as informações ao Banking Server
	 6. Resposta da etapa 5
	 7. Resposta da etapa 4
	 8. O servidor verifica as informações e responde com uma falha do 401 se o procedimento falhou ou com um Token JWT.
	 9. Resposta da etapa 2
	 
	- **Autorização** 
![Authorization Pipeline](https://github.com/eduardo-fillipe/labsit-banking-api/blob/main/readme-images/authorization.png?raw=true)
	1. Realiza uma requisição de transação ao Labsit Banking Server. Essa requisição contem o Token JWT previamente adiquirido durante a fase de autorização.
	2. Gateway solicita que o sistema de segurança valide o token enviado pelo cliente.
	3. Sistema de segurança solicita informações adicionais necessárias para a autorização.
	4. Gateway repassa a solicitação 3 ao sistema responsável.
	5. O sistema responsável responde a solicitação da etapa 4.
	6. Resposta da solicitação da etapa 3.
	7. Resposta da etapa 2, com um OK(200) ou erro de Authorização(403)
	8. Uma vez que o token JWT foi devidamente autorizado, o gateway repassa a solicitação da etapa 1 para o sistema responsável por atendê-la.
	9. Resposta da solicitação da etapa 9.
	10. Resposta do Gateway de Api para o cliente com uma falha de autorização (caso ocorra erro na etapa 7) ou com a resposta do serviço alvo do cliente. 
