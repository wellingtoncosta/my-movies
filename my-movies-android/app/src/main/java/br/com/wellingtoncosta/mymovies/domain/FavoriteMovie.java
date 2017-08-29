package br.com.wellingtoncosta.mymovies.domain;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author Wellington Costa on 30/04/17.
 */
public class FavoriteMovie extends RealmObject {

    @PrimaryKey
    private Long id;

    private Long userId;

    private String movieTitle;

    private String movieYear;

    private String movieGenre;

    private String movieImageUrl;


    public FavoriteMovie() {}

    public FavoriteMovie(FavoriteMovieTO favoriteMovieTO) {
        this.id = favoriteMovieTO.getId();
        this.userId = favoriteMovieTO.getUser().getId();
        this.movieGenre = favoriteMovieTO.getMovie().getGenre();
        this.movieImageUrl = favoriteMovieTO.getMovie().getImageUrl();
        this.movieTitle = favoriteMovieTO.getMovie().getTitle();
        this.movieYear = favoriteMovieTO.getMovie().getYear();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getMovieImageUrl() {
        return movieImageUrl;
    }

    public void setMovieImageUrl(String movieImageUrl) {
        this.movieImageUrl = movieImageUrl;
    }
}
