import angular from "angular";
import uirouter from "angular-ui-router";
import component from "./login.component";
import service from "./login.service";

export default angular.module("login", [ uirouter ])
  .config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise("/login");

    $stateProvider
      .state("login", {
        url: "/login",
        template: "<login></login>"
      });
  })
  .component("login", component)
  .service("LoginService", service);