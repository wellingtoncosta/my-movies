export default class LoginService {

  API_URL = "https://my-movies-api.herokuapp.com";
  //API_URL = "http://localhost:8080";

  constructor($http, $rootScope) {
    this.http = $http;
    this.rootScope = $rootScope;
  }

  login(user) {
    return this.http.post(`${this.API_URL}/login`, user, {
      headers: { "Content-Type": "application/json" }
    });
  }

  setCredentials(user, token) {
    const { id, name, email } = user;
    this.rootScope.globals = {
      currentUser: { id, email, name }
    };

    localStorage.setItem("token", token);
  }

  getToken() {
    return localStorage.getItem("token");
  }

  logout() {
    localStorage.clear();
    this.rootScope.globals = {};
  }
}