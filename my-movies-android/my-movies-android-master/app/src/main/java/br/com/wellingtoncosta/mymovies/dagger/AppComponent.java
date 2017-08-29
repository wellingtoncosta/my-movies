package br.com.wellingtoncosta.mymovies.dagger;

import javax.inject.Singleton;

import br.com.wellingtoncosta.mymovies.ui.SaveUserActivity;
import br.com.wellingtoncosta.mymovies.ui.LoginActivity;

import br.com.wellingtoncosta.mymovies.ui.MoviesActivity;
import br.com.wellingtoncosta.mymovies.ui.SplashActivity;
import br.com.wellingtoncosta.mymovies.ui.UserRegistrationActivity;
import br.com.wellingtoncosta.mymovies.ui.fragment.FavoriteMoviesFragment;
import br.com.wellingtoncosta.mymovies.ui.fragment.MoviesFragment;
import dagger.Component;

/**
 * @author Wellington Costa on 30/04/17.
 */
@Singleton
@Component(modules = {NetworkModule.class, AppModule.class})
public interface AppComponent {

    void inject(SplashActivity activity);
    void inject(SaveUserActivity activity);
    void inject(LoginActivity activity);
    void inject(UserRegistrationActivity activity);
    void inject(MoviesActivity activity);
    void inject(MoviesFragment fragment);
    void inject(FavoriteMoviesFragment fragment);

}
