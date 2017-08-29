import angular from "angular";
import uirouter from "angular-ui-router";
import component from "./movies.component";
import service from "./movies.service";

export default angular.module("movies", [ uirouter ])
  .config(($stateProvider, $urlRouterProvider) => {

    $stateProvider
      .state("movies", {
        url: "/movies",
        template: "<movies></movies>"
      });
  })
  .component("movies", component)
  .service("MoviesService", service);