import angular from "angular";
import uirouter from "angular-ui-router";
import component from "./newmovie.component";

export default angular.module("newmovie", [ uirouter ])
  .config(($stateProvider, $urlRouterProvider) => {

    $stateProvider
      .state("newmovie", {
        url: "/newmovie",
        template: "<newmovie></newmovie>"
      });
  })
  .component("newmovie", component);