import angular from "angular";

import login from "./login/login";
import movies from "./movies/movies";
import newMovie from "./newmovie/newmovie";

export default angular.module("app.components", [
  login.name,
  movies.name,
  newMovie.name
]);