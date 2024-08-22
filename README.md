# Sistema de Gerenciamento de Processos
<img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" width="40" height="40"/>

Este projeto é um microsserviço desenvolvido em Spring Boot para o gerenciamento de processos judiciais e seus réus.
A aplicação permite o cadastro, consulta, atualização e exclusão de processos, bem como a adição de réus aos processos cadastrados.

## Tecnologias Utilizadas
<img loading="lazy" src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" width="40" height="40"/>

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Liquibase (para controle de versões do banco de dados)
- JUnit (para testes unitários)
- Swagger (para documentação da API)
- Lombok (para redução de boilerplate no código)

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em seu ambiente:

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Git](https://git-scm.com/)

## Configuração do Banco de Dados

1. **Criar o banco de dados no PostgreSQL:**

   Abra o terminal ou seu cliente favorito do PostgreSQL (como pgAdmin) e execute o seguinte comando para criar o banco de dados:
  ou mude banco no projeto conforme queira ou deixe o banco padrao postgres.

   ```sql
   CREATE DATABASE adminprocessos; 

no projeto temos application.yml: 

spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/adminprocessos(ou seu banco que queira utilizar)
    username: seu_usuario
    password: sua_senha
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  liquibase:
    change-log: classpath:/db/changelog/db.changelog-master.sql


## Documentação da API
A documentação da API é gerada automaticamente pelo Swagger.
Após iniciar a aplicação, você pode acessar a documentação interativa através da URL:

http://localhost:8080/swagger-ui/index.html

## Testes

-- Testes Unitários
Os testes unitários estão localizados no pacote com.douglas.dev.process e podem ser executados com o comando: 
Esses testes cobrem a funcionalidade do serviço e o comportamento das exceções.

-- Testes de Integração
Os testes de integração também estão localizados no pacote com.douglas.dev.process e podem ser executados com o mesmo comando:
 
## Esses testes simulam requisições reais para os endpoints da API e verificam o comportamento esperado. Alguns dos testes de integração incluídos são:


- deveSalvarProcesso: Verifica se um novo processo é salvo corretamente.
- deveAdicionarReuAoProcesso: Verifica se um réu é adicionado a um processo existente.
- deveListarTodosProcessos: Verifica se a listagem de todos os processos funciona corretamente.
- deveConsultarProcessoPorNumero: Verifica se a consulta por número de processo retorna os dados corretos.
- deveExcluirProcesso: Verifica se um processo é excluído corretamente.
- deveLancarExcecaoQuandoProcessoNaoEncontrado: Verifica se a exceção correta é lançada ao tentar consultar um processo inexistente.
