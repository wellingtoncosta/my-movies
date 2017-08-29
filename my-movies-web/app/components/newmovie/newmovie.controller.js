export default class MoviesController {

  constructor($mdDialog, $state, MoviesService) {
    this.dialog = $mdDialog;
    this.state = $state;
    this.service = MoviesService;
    this.movie = {};
  }

  backToMovies() {
    this.state.go("movies");
  }

  saveNewMovie(movie) {
    this.service.saveNewMovie(movie).$promise.then((response) => {
      this.movie = {};
      this.backToMovies();
    }).catch((error) => {
      this.showDialog("Falha", "Houve uma falha ao salvar o filme.");
      console.error(error);
    });
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