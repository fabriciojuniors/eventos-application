
# Gestão de eventos (back-end)

Projeto destinado a realização de gestão de eventos de uma institução, permitindo o cadastro de instituições e eventos.

## Funcionamento
- Dado que seja realizado o cadastro de uma instituição é habilitado o cadastro de eventos.
- As instituições possuem nome e tipo.
- Os eventos possuem nome, data/hora de início, data/hora de finalização e indicativo se está ativo. Este indicativo é gerido automaticamente pela aplicação.
- A cada intervalo (configurado em `application.properties`) é executado um `scheduler` que irá identificar os eventos pendentes de encerramento, encerrá-los e enviar uma notificação via `web socket`.
- Uma vez encerrados, não é possível realizar alterações em eventos.


## Tecnologias

- Java 21
- PostgreSQL
- Quarkus
- Mockito/JUnit
## Executando localmente

Para fazer o deploy desse projeto rode

```bash
  ./mvnw quarkus:dev
```


## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  ./mvnw quarkus:test
```


## Documentação da API
**Localmente acessar:** [SwaggerUI](http://localhost:8080/q/swagger-ui/)
