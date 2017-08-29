package br.com.wellingtoncosta.mymovies.web.controller;

import br.com.wellingtoncosta.mymovies.domain.FavoriteMovie;
import br.com.wellingtoncosta.mymovies.domain.Movie;
import br.com.wellingtoncosta.mymovies.service.movie.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Wellington Costa on 29/04/17.
 */
@RestController
@RequestMapping("api/movies")
public class MovieController {

    @Inject
    private MovieService movieService;

    @RequestMapping(method=GET, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> findAllMovies() {
        List<Movie> movies = movieService.findAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=GET, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> findMovieById(@PathVariable Long id) {
        Movie movie = movieService.findMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @RequestMapping(value="/title/{title}", method=GET, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> findAllMoviesByTitle(@PathVariable String title) {
        List<Movie> movies = movieService.findAllMoviesByTitle(title);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @RequestMapping(method=POST, consumes=APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> saveNewMovie(@RequestBody Movie movie) {
        Movie newMovie= movieService.saveNewMovie(movie);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=DELETE, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/favorites", method=GET, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FavoriteMovie>> findAllFavoriteMovies() {
        List<FavoriteMovie> favoriteMovies = movieService.findAllFavoriteMovies();
        return new ResponseEntity<>(favoriteMovies, HttpStatus.OK);
    }

    @RequestMapping(value="/favorites/title/{title}", method=GET, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FavoriteMovie>> findAllFavoriteMoviesByTitle(@PathVariable String title) {
        List<FavoriteMovie> favoriteMovies = movieService.findAllFavoriteMoviesByTitle(title);
        return new ResponseEntity<>(favoriteMovies, HttpStatus.OK);
    }

    @RequestMapping(value="/favorites", method=POST, consumes=APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<FavoriteMovie> saveNewFavoriteMovie(@RequestBody Movie movie) {
        FavoriteMovie newFavoriteMovie= movieService.saveNewFavoriteMovie(movie);
        return new ResponseEntity<>(newFavoriteMovie, HttpStatus.CREATED);
    }

    @RequestMapping(value="/favorites/{movieId}", method=DELETE, produces=APPLICATION_JSON_VALUE)
    public ResponseEntity<FavoriteMovie> deleteFavoriteMovie(@PathVariable Long movieId) {
        movieService.deleteFavoriteMovie(movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
