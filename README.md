# Projeto CRUD - Angular + Spring Boot

Este projeto implementa um sistema CRUD utilizando **Angular** no frontend e **Spring Boot 3** no backend. O frontend consome a API do backend usando o `HttpClient` do Angular, e o backend utiliza o **MySQL** como banco de dados.

## Tecnologias Usadas

- **Frontend**: Angular 15+
- **Backend**: Spring Boot 3
- **Banco de Dados**: MySQL

## Como Rodar

### Backend (Spring Boot)

1. Clone o repositório e navegue até a pasta do backend.
2. Configure a conexão com o banco de dados no arquivo `src/main/resources/application.yml`:
   
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/nome_do_banco
       username: usuario
       password: senha
     jpa:
       hibernate:
         ddl-auto: update
       show-sql: true
   ```

3. Execute o comando para rodar o Spring Boot:

   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend (Angular)

1. Navegue até a pasta do frontend (`projeto-crud`).
2. Instale as dependências do projeto:

   ```bash
   npm install
   ```

3. Execute o Angular:

   ```bash
   npm start
   ```

O projeto estará disponível em:

- **Frontend**: [http://localhost:4200](http://localhost:4200)
- **Backend**: [http://localhost:8080](http://localhost:8080)

## Funcionalidades

- Cadastro, edição e exclusão de produtos.
- Listagem de dados com interatividade.

## Contribuição

Sinta-se à vontade para contribuir! Para isso, siga os passos abaixo:

1. Faça o fork do repositório.
2. Crie uma branch para suas alterações:

   ```bash
   git checkout -b feature/nova-funcionalidade
   ```

3. Faça commit das suas mudanças:

   ```bash
   git commit -am 'Adiciona nova funcionalidade'
   ```

4. Envie a branch:

   ```bash
   git push origin feature/nova-funcionalidade
   ```

5. Abra um pull request.

## Licença

Este projeto é licenciado sob a **MIT License**.