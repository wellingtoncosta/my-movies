package br.com.wellingtoncosta.mymovies.repository.movie;

import br.com.wellingtoncosta.mymovies.domain.FavoriteMovie;
import br.com.wellingtoncosta.mymovies.domain.Movie;
import br.com.wellingtoncosta.mymovies.domain.User;

import java.util.List;

/**
 * @author Wellington Costa on 29/04/17.
 */
public interface MovieRepository {

    List<Movie> findAllMovies();

    Movie findMovieById(Long id);

    List<Movie> findAllMoviesByTitle(String title);

    Movie saveNewMovie(Movie movie);

    void deleteMovie(Long id);

    void deleteAllMovies();

    List<FavoriteMovie> findAllFavoriteMovies(User user);

    List<FavoriteMovie> findAllFavoriteMoviesByTitle(User user, String title);

    List<FavoriteMovie> findAllFavoriteMoviesByMovie(Long movieId);

    FavoriteMovie findFavoriteMovieByMovie(Long movieId, Long userId);

    FavoriteMovie saveNewFavoriteMovie(Movie movie, User user);

    void deleteFavoriteMovie(FavoriteMovie favoriteMovie);

}
