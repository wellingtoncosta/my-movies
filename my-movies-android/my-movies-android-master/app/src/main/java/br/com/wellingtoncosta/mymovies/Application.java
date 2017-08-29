package br.com.wellingtoncosta.mymovies;

import br.com.wellingtoncosta.mymovies.dagger.AppComponent;
import br.com.wellingtoncosta.mymovies.dagger.AppModule;
import br.com.wellingtoncosta.mymovies.dagger.DaggerAppComponent;
import br.com.wellingtoncosta.mymovies.dagger.NetworkModule;

/**
 * @author Wellington Costa on 30/04/17.
 */
public class Application extends android.app.Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public AppComponent component() {
        return appComponent;
    }
}
