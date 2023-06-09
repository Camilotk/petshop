<h1 align="center">
  <img src="https://uploaddeimagens.com.br/images/004/433/031/original/petshop-removebg-preview.png" alt="ocaml">
</h1>
<div align="center">

![build status](https://img.shields.io/github/actions/workflow/status/Camilotk/petshop/ci.yml?style=flat-square)
![lines of code](https://img.shields.io/tokei/lines/github/Camilotk/petshop?style=flat-square)
![commit activity](https://img.shields.io/github/commit-activity/m/Camilotk/petshop?style=flat-square)
![last commit](https://img.shields.io/github/last-commit/Camilotk/petshop?style=flat-square)

</div>

API em Spring Boot de um Sistema de Controle de atendimentos de petshops.

## Como rodar o projeto

### Executar o Projeto no Ambiente Local

##### Pré-requisitos
- Java 17 ou superior
- Maven
- PostgreSQL
- Redis

##### Configuração
Configurar as chaves de sistema:
```
POSTGRES_HOST=
POSTGRES_PORT=
POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=
REDIS_HOST=
REDIS_PORT=
JWT_SECRET=
```

> O valor de JWT_SECRET deve ter um tamanho maior que 256 bits.

##### Comando

Após configurar as variavéis de ambiente, dentro do terminal bash rode:
```
./mvnw spring-boot:run
```

### Executar no Docker

Rode o comando clean para remover qualquer build antiga.
```
./mvnw clean
```
Então rode o comando package para gerar um novo .jar do projeto.
```
./mvnw package
```
E por fim rode o docker-compose.
```
docker-compose up
```

### Executar os Testes
Dentro do terminal bash rode
```
./mvnw test
```

### Documentação Swagger
Após a aplicação estar rodando, basta acessar o endereço raiz no navegador (para localhost padrão [http://localhost:8080](#)) e a documentação vai abrir.

> Caso prefira você pode baixar [essa configuração](./petshop.yaml) do [Insomnia](https://insomnia.rest/) e utilizá-la para testar a API.

## Requisitos
<details>

![](https://uploaddeimagens.com.br/images/004/433/038/original/objetivo_back_petshop.png)

</details>

## Conceitos utilizados
- [MVC Pattern](https://pt.wikipedia.org/wiki/MVC)
- [The Twelve Factor App](https://12factor.net/pt_br/)
- [JWT Stateless Authentication](https://jwt.io/)
- [Richardson API Maturity Model Level 2](https://en.m.wikipedia.org/wiki/Richardson_Maturity_Model)
