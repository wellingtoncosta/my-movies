# my-movies-android

## Table of Contents

- [Available Build Variants](#available-build-variants)
  - [Development](#development)
  - [Production](#production)
- [Considerations](#considerations)
- [Technologies](#technologies)

## Available Build Variants

### `Development`

Run app with `developmentDebug` build variant to consume the [API](https://github.com/WellingtonCosta/my-movies-api) in [development environment](https://github.com/WellingtonCosta/my-movies-api/blob/master/README.md#development).<br>

### `Production`

Run app with `productionDebug` build variant to consume the [API](https://github.com/WellingtonCosta/my-movies-api) in [production environment](https://github.com/WellingtonCosta/my-movies-api/blob/master/README.md#production) avaliable on [Heroku](https://my-movies-api.herokuapp.com/).<br>

## Considerations

Due to [Heroku Free Dynos](https://devcenter.heroku.com/articles/free-dyno-hours) sleep after no traffic in a 30 minute period, the application may take a time to respond.

## Technologies

----------------------------
> Gradle

> Google Material Design

> Butterknife

> Dagger 2

> Retrofit 2

> OkHttp 3

> Realm

> Fresco

> [MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView)

> [MaterialDrawer](https://github.com/mikepenz/MaterialDrawer)
