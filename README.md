# Challenge ONE | Back End | Alura Forum

<p align="center" >
     <img width="200" heigth="200" src="https://user-images.githubusercontent.com/78982435/209698701-28dedb2e-855b-44b2-8872-afa45e3b35aa.png">
</p>


## Sobre o projeto
O fórum da Alura é uma API REST de discussão online onde usuáros podem compartilhar dúvidas, respostas e experiências.
## Tecnologias
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [JWT](https://jwt.io/)
- [SpringDoc OpenAPI 3](https://springdoc.org/)
- [Mysql](https://dev.mysql.com/downloads/)

## Práticas Adotadas
- API REST
- Validação de dados com anotações do Spring validations e Hibernate
- Consultas com Spring Data JPA
- Tratamento de respostas de erro
- Proteção de endpoints e recursos
- Autenticação e autorização
- Gestão de usuários e permições
- Geração automática do Swagger com a OpenAPI 3
## Como Executar
- Para executar o projeto, você precisará do Java a partir da versão 17.
- Baixe o executável aqui: link para o executável.
- Para executar o projeto, abra um terminal e navegue até a pasta onde o executável foi baixado. Em seguida, execute o seguinte comando:

```
 java -jar forum-0.0.1-SNAPSHOT.jar 
```
Se o banco de dados não estiver disponível, você pode fornecer um para o projeto, as tabelas e dados serão gerados automaticamente. Para isso, configure as seguintes variáveis de ambiente:

- MYSQL_HOST (URL do banco de dados, incluindo o nome)
- MYSQL_USERNAME (Nome do usuário do banco de dados)
- MYSQL_PASSWORD (Senha do banco de dados)

Em seguida, execute o seguinte comando em um terminal:
```
java -DMYSQL_HOST=URL_PARA_O_BANCO -DMYSQL_USERNAME=NOME_DO_USUARIO -DMYSQL_PASSWORD=SENHA -jar forum-0.0.1-SNAPSHOT.jar 
```

## Instruções para acessar a API
- A API pode ser acessada em [localhost:8080](http://localhost:8080).
- O Swagger pode ser visualizado em [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## Instruções para fazer login

- Para fazer login como administrador, use as seguintes credenciais:
```
{
  "email": "admin@email.com",
  "senha": "admin1234"
}
```
- Para fazer login como um usuário, use as seguintes credenciais:
```
{
  "email": "user@email.com",
  "senha": "user1234"
}
``` 
## Diagrama Entidade Relacionamento
![forum_alura_DER](https://github.com/wesley-brwno/challenge-one-forum-alura/assets/84514966/e3c0e9ee-1439-4c12-a537-5cb99c92bdf2)

## Endpoints da API
<table border="1">
    <tr>
        <th>método</th>
        <th>URI</th>
        <th>descrição</th>
        <th>perfil</th>
    </tr>
    <tr>
        <td>POST</td>
        <td>/auth/entrar</td>
        <td>permite que usuário efetue log in</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/auth/cadastar</td>
        <td>permite que usuário efetue cadastro na applicação</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/cursos</td>
        <td>lista todos os cursos</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/cursos</td>
        <td>cadastra um novo curso</td>
        <td>ADMIN</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/cursos/{id}</td>
        <td>lista um curso por id</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/cursos/{id}</td>
        <td>deleta um curso por id</td>
        <td>ADMIN</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/respostas</td>
        <td>lista todas as respostas pelo id de um tópico</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/respostas</td>
        <td>cria uma resposta para um post</td></td>
        <td>ADMIN | USER</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/respostas</td>
        <td>atualiza uma resposta</td>
        <td>ADMIN | USER</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/respostas/{id}</td>
        <td>marca uma resposta como correta ou incorreta usando seu id</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/respostas/{id}</td>
        <td>deleta uma resposta por id</td>
        <td>ADMIN | USER</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/topicos</td>
        <td>lista todos os tópicos</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/topicos</td>
        <td>cadastra um tópico</td>
        <td>ADMIN | USER</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/topicos/{ativo}/{id}</td>
        <td>muda o status de um tópico para FECHADO pelo id</td>
        <td>ADMIN | USER</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/topicos/{id}</td>
        <td>busca um tópico pelo id</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/topicos/{id}</td>
        <td>atualiza um tópico pelo id</td>
        <td>USER</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/topicos/{id}</td>
        <td>deleta um tópico pelo id</td>
        <td>ADMIN | USER</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/topicos/{topico_id}/respostas</td>
        <td>lista tópico pelo id com suas respostas</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/topicos/ano/{ano}</td>
        <td>lista tópicos por ano</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/topicos/cursos/{nome}</td>
        <td>lista tópicos pelo nome do curso</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/topicos/status</td>
        <td>lista tópicos por status</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/usuarios/{id}</td>
        <td>lista usuário pelo id</td>
        <td>N/A</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/usuarios/{id}</td>
        <td>atualiza usuário pelo id</td>
        <td>ADMIN | USER</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/usuarios/{id}</td>
        <td>deleta usuário pelo id</td>
        <td>ADMIN | USER</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/usuarios</td>
        <td>lista todos os usuários</td>
        <td>ADMIN</td>
    </tr>
</table>

![Inserirjh um título](https://user-images.githubusercontent.com/101230741/188219675-46a897f5-7a17-4593-b026-088bc6afd7b9.png)