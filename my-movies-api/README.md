# my-movies-api

## Table of Contents

- [Available Environments](#available-environments)
  - [Development](#development)
  - [Test](#test)
  - [Production](#production)
- [Considerations](#considerations)
- [Technologies](#technologies)

## Available Environments

### `Development`

First, you must create a database called `mymovies` on PostgreSQL. See [development properties](https://github.com/WellingtonCosta/my-movies-api/blob/master/src/main/resources/application-dev.properties).<br>
Run `mvn clean compile spring-boot:run` on project root folder. It will be avaliable at [http://localhost:8080](http://localhost:8080).<br>

### `Test`

First, you must create a database called `mymovies_test` on PostgreSQL. See [test properties](https://github.com/WellingtonCosta/my-movies-api/blob/master/src/test/resources/application.properties).<br>
Run `mvn test` on project root folder.<br>

### `Production`

Run `mvn clean compile spring-boot:run -Pprod` on project root folder to run it in production environment.<br>
NOTE: This is available in production environment on [Heroku](https://my-movies-api.herokuapp.com). Check it out!<br>

## Considerations

Due to [Heroku Free Dynos](https://devcenter.heroku.com/articles/free-dyno-hours) sleep after no traffic in a 30 minute period, the application may take a time to respond.

## Technologies

----------------------------
> Java 8

> Maven

> Spring (Boot / Data / Security / Test / Web)

> JSON Web Token

> JPA

> QueryDSL

> Flyway Database Migrations

> PostgreSQL
