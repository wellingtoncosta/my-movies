package br.com.wellingtoncosta.mymovies.repository.movie;

import br.com.wellingtoncosta.mymovies.domain.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Wellington Costa on 29/04/17.
 */
@Repository("movieRepository")
public class MovieRepositoryImp implements MovieRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private JPAQueryFactory queryFactory;

    @Override
    public List<Movie> findAllMovies() {
        return queryFactory
                .selectFrom(QMovie.movie)
                .fetch();
    }

    @Override
    public Movie findMovieById(Long id) {
        return entityManager.find(Movie.class, id);
    }

    @Override
    public List<Movie> findAllMoviesByTitle(String title) {
        QMovie movie = QMovie.movie;

        return queryFactory
                .selectFrom(movie)
                .where(movie.title.startsWithIgnoreCase(title))
                .fetch();
    }

    @Override
    public Movie saveNewMovie(Movie movie) {
        entityManager.persist(movie);
        return movie;
    }

    @Override
    public void deleteMovie(Long id) {
        Movie movie = entityManager.find(Movie.class, id);
        entityManager.remove(movie);
    }

    @Override
    public void deleteAllMovies() {
        List<Movie> movies = findAllMovies();

        movies.forEach(movie -> {
            entityManager.remove(movie);
        });
    }

    @Override
    public List<FavoriteMovie> findAllFavoriteMovies(User user) {
        QFavoriteMovie queryFavoriteMovie = QFavoriteMovie.favoriteMovie;
        QMovie queryMovie = QMovie.movie;
        QUser queryUser = QUser.user;

        return queryFactory
                .selectFrom(queryFavoriteMovie)
                .join(queryFavoriteMovie.movie, queryMovie)
                .join(queryFavoriteMovie.user, queryUser)
                .where(queryUser.id.eq(user.getId()))
                .fetch();
    }

    @Override
    public List<FavoriteMovie> findAllFavoriteMoviesByTitle(User user, String title) {
        QFavoriteMovie queryFavoriteMovie = QFavoriteMovie.favoriteMovie;
        QMovie queryMovie = QMovie.movie;
        QUser queryUser = QUser.user;

        return queryFactory
                .selectFrom(queryFavoriteMovie)
                .join(queryFavoriteMovie.movie, queryMovie)
                .join(queryFavoriteMovie.user, queryUser)
                .where(queryUser.id.eq(user.getId()))
                .where(queryFavoriteMovie.movie.title.startsWithIgnoreCase(title))
                .fetch();
    }

    @Override
    public List<FavoriteMovie> findAllFavoriteMoviesByMovie(Long movieId) {
        QFavoriteMovie queryFavoriteMovie = QFavoriteMovie.favoriteMovie;
        QMovie queryMovie = QMovie.movie;

        return queryFactory
                .selectFrom(queryFavoriteMovie)
                .join(queryFavoriteMovie.movie, queryMovie)
                .where(queryMovie.id.eq(movieId))
                .fetch();
    }

    @Override
    public FavoriteMovie findFavoriteMovieByMovie(Long movieId, Long userId) {
        QFavoriteMovie queryFavoriteMovie = QFavoriteMovie.favoriteMovie;
        QMovie queryMovie = QMovie.movie;
        QUser queryUser = QUser.user;

        return queryFactory
                .selectFrom(queryFavoriteMovie)
                .join(queryFavoriteMovie.movie, queryMovie)
                .join(queryFavoriteMovie.user, queryUser)
                .where(queryMovie.id.eq(movieId))
                .where(queryUser.id.eq(userId))
                .fetchOne();
    }

    @Override
    public FavoriteMovie saveNewFavoriteMovie(Movie movie, User user) {
        movie = entityManager.find(Movie.class, movie.getId());
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setMovie(movie);
        favoriteMovie.setUser(user);

        entityManager.persist(favoriteMovie);

        return favoriteMovie;
    }

    @Override
    public void deleteFavoriteMovie(FavoriteMovie favoriteMovie) {
        entityManager.remove(favoriteMovie);
    }
}