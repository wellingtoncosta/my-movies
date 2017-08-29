import "angular-material/angular-material.min.css";
import "./assets/style.css";

import angular from "angular";
import animate from "angular-animate";
import aria from "angular-aria";
import material from "angular-material";
import messages from "angular-messages";
import resources from "angular-resource";
import uirouter from "angular-ui-router";

import components from "./components/components";
import authInterceptor from "./components/auth/auth.interceptor.js";

angular.module("mymovies", [
  animate,
  aria,
  material,
  messages,
  resources,
  uirouter,
  components.name
])
  .service("authInterceptor", authInterceptor)
  .config(($mdThemingProvider, $httpProvider) => {
    $mdThemingProvider.theme("default").primaryPalette("blue");
    $httpProvider.interceptors.push("authInterceptor");
  })
  .run(function ($rootScope, $state) {
    $rootScope.$on("$stateChangeStart", (event, toState, toParams, fromState, fromParams, options) => {
      const { name } = toState;
      if ((!$rootScope.globals || !$rootScope.globals.currentUser) && name !== "login") {
        event.preventDefault();
        $state.go("login");
      }
    });
  });