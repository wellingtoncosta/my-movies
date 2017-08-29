export default class MoviesController {

  constructor($mdDialog, $state, MoviesService, LoginService) {
    this.dialog = $mdDialog;
    this.state = $state;
    this.movieService = MoviesService;
    this.loginService = LoginService;
    this.movies = [];

    this.getAllMovies();
  }

  getAllMovies() {
    this.movies = [];

    this.movieService.getAllMovies().$promise.then((response) => {
      response.forEach((movie) => {
        this.movies.push(movie);
      }, this);
    }).catch((error) => {
      this.showDialog("Falha", "Houve uma falha ao buscar os filmes.");
      console.error(error);
    });
  }

  deleteMovie(movie) {
    const { id } = movie;
    this.movieService.deleteMovie({ id }).$promise.then((response) => {
      this.getAllMovies();
    }).catch((error) => {
      this.showDialog("Falha", "Houve uma falha ao remover o filme.");
      console.error(error);
    });
  }

  addNewMovie() {
    this.state.go("newmovie");
  }

  logout() {
    this.loginService.logout();
    this.state.go("login");
  }

  showDialog(title, message) {
    const confirm = this.dialog.alert()
      .title(title)
      .textContent(message)
      .ariaLabel("Dialog")
      .ok("Ok");

    this.dialog.show(confirm);
  }
}