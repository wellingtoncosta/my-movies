package br.com.wellingtoncosta.mymovies.service.movie;

import br.com.wellingtoncosta.mymovies.domain.FavoriteMovie;
import br.com.wellingtoncosta.mymovies.domain.Movie;
import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.repository.movie.MovieRepository;
import br.com.wellingtoncosta.mymovies.service.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.isNull;


/**
 * @author Wellington Costa on 29/04/17.
 */
@Transactional
@Service("movieService")
public class MovieServiceImp implements MovieService {

    @Inject
    private MovieRepository repository;

    @Inject
    private UserService userService;

    @Override
    public List<Movie> findAllMovies() {
        return adjustMovies(repository.findAllMovies());
    }

    @Override
    public Movie findMovieById(Long id) {
        return repository.findMovieById(id);
    }

    @Override
    public List<Movie> findAllMoviesByTitle(String title) {
        return adjustMovies(repository.findAllMoviesByTitle(title));
    }

    private List<Movie> adjustMovies(List<Movie> movies) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(email);

        // Check movie is favorite
        if (!isNull(user) && !userService.isAdmin(user)) {
            movies.forEach(adjustedMovie -> {
                FavoriteMovie favoriteMovie = repository.findFavoriteMovieByMovie(adjustedMovie.getId(), user.getId());
                adjustedMovie.setFavorite(!isNull(favoriteMovie));
            });
        }

        return movies;
    }

    @Override
    public Movie saveNewMovie(Movie movie) {
        return repository.saveNewMovie(movie);
    }

    @Override
    public void deleteMovie(Long id) {
        List<FavoriteMovie> favoriteMovies = repository.findAllFavoriteMoviesByMovie(id);

        favoriteMovies.forEach(favoriteMovie -> {
            repository.deleteFavoriteMovie(favoriteMovie);
        });

        repository.deleteMovie(id);
    }

    @Override
    public void deleteAllMovies() {
        repository.deleteAllMovies();
    }

    @Override
    public List<FavoriteMovie> findAllFavoriteMovies() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(email);
        return repository.findAllFavoriteMovies(user);
    }

    @Override
    public List<FavoriteMovie> findAllFavoriteMoviesByTitle(String title) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(email);
        return repository.findAllFavoriteMoviesByTitle(user, title);
    }

    @Override
    public FavoriteMovie saveNewFavoriteMovie(Movie movie) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(email);
        return repository.saveNewFavoriteMovie(movie, user);
    }

    @Override
    public void deleteFavoriteMovie(Long movieId) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(email);
        FavoriteMovie favoriteMovie = repository.findFavoriteMovieByMovie(movieId, user.getId());
        repository.deleteFavoriteMovie(favoriteMovie);
    }
}
