import template from "./movies.html";
import controller from "./movies.controller.js";

export default {
  restrict: "E",
  scope: {},
  template,
  controller,
  controllerAs: "ctrl",
  bindToController: true
};