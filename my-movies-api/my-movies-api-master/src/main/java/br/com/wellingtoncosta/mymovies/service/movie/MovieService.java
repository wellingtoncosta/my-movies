package br.com.wellingtoncosta.mymovies.service.movie;

import br.com.wellingtoncosta.mymovies.domain.FavoriteMovie;
import br.com.wellingtoncosta.mymovies.domain.Movie;
import br.com.wellingtoncosta.mymovies.domain.User;

import java.util.List;

/**
 * @author Wellington Costa on 29/04/17.
 */
public interface MovieService {

    List<Movie> findAllMovies();

    Movie findMovieById(Long id);

    List<Movie> findAllMoviesByTitle(String title);

    Movie saveNewMovie(Movie movie);

    void deleteMovie(Long id);

    void deleteAllMovies();

    List<FavoriteMovie> findAllFavoriteMovies();

    List<FavoriteMovie> findAllFavoriteMoviesByTitle(String title);

    FavoriteMovie saveNewFavoriteMovie(Movie movie);

    void deleteFavoriteMovie(Long movieId);

}
