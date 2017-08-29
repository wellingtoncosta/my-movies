export default class MoviesService {

  API_URL = "https://my-movies-api.herokuapp.com";
  //API_URL = "http://localhost:8080";

  constructor($resource, LoginService) {
    this.resource = $resource(`${this.API_URL}/api/movies/:id`, { id: "@id" }, {
      query: {
        method: "GET",
        isArray: true
      },
      save: {
        method: "POST"
      },
      delete: {
        method: "DELETE"
      }
    });
  }

  getAllMovies() {
    return this.resource.query();
  }

  saveNewMovie(movie) {
    return this.resource.save(movie);
  }

  deleteMovie(movie) {
    return this.resource.delete(movie);
  }

}