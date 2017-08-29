package br.com.wellingtoncosta.mymovies.web;

import br.com.wellingtoncosta.mymovies.TestTemplate;
import br.com.wellingtoncosta.mymovies.domain.Movie;
import br.com.wellingtoncosta.mymovies.service.movie.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Wellington Costa on 14/05/17.
 */
public class MovieControllerTest extends TestTemplate {

    @Inject
    private MovieService movieService;

    private static final String URL = "/api/movies/";

    private MockHttpServletRequestBuilder request;

    private Movie movie;

    private Movie theDaVinciCode = Movie.builder()
            .title("O Código Da Vinci")
            .genre("Suspense")
            .year("2006")
            .imageUrl("http://lorempixel.com/400/400/")
            .build();

    private Movie angelsAndDemons = Movie.builder()
            .title("Anjos e Demonios")
            .genre("Suspense")
            .year("2009")
            .imageUrl("http://lorempixel.com/400/400/")
            .build();

    private Movie inferno = Movie.builder()
            .title("Inferno")
            .genre("Suspense")
            .year("2016")
            .imageUrl("http://lorempixel.com/400/400/")
            .build();


    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void listMovies() throws Exception {
        givenThatHaveNoMoives();

        thenListAllMovies();

        whenReceiveAListWithSize(0);
    }

    @Test
    public void saveNewMovie() throws Exception {
        givenThatHaveANewMovie(angelsAndDemons);

        whenSaveANewMovie();

        thenShouldReceiveAMovie(angelsAndDemons);
    }

    @Test
    public void listSavedMovies() throws Exception {
        givenThatHaveThreeMovies();

        thenListAllMovies();

        whenReceiveAListWithSize(3);
    }

    @Test
    public void deleteMovie() throws Exception {
        givenThatHaveAMovieAlreadySaved(angelsAndDemons);

        thenTryToDeleteTheMovie();

        whenReceiveAStatus(status().isNoContent());
    }


    private void givenThatHaveNoMoives() {
        movieService.deleteAllMovies();
    }

    private void thenListAllMovies() {
        request = get(URL)
                .header(HttpHeaders.AUTHORIZATION, token);
    }

    private void whenReceiveAListWithSize(int size) throws Exception {
        mvc.perform(request).andExpect(jsonPath("$", hasSize(size)));
    }


    private void givenThatHaveANewMovie(Movie movie) {
        this.movie  = movie;
    }

    private void whenSaveANewMovie() {
        request = post(URL)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(toJson(this.movie))
                .contentType(contentType);
    }

    private void thenShouldReceiveAMovie(Movie movie) throws Exception {
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(movie.getTitle())))
                .andExpect(jsonPath("$.genre", is(movie.getGenre())))
                .andExpect(jsonPath("$.year", is(movie.getYear())));
    }


    private void givenThatHaveThreeMovies() {
        movieService.saveNewMovie(theDaVinciCode);
        movieService.saveNewMovie(angelsAndDemons);
        movieService.saveNewMovie(inferno);
    }

    private void givenThatHaveAMovieAlreadySaved(Movie movie) {
        this.movie = movieService.saveNewMovie(movie);
    }

    private void thenTryToDeleteTheMovie() {
        request = delete(URL + movie.getId())
                .header(HttpHeaders.AUTHORIZATION, token);
    }

    private void whenReceiveAStatus(ResultMatcher status) throws Exception {
        mvc.perform(request).andExpect(status);
    }

}
