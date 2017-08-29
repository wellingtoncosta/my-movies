package br.com.wellingtoncosta.mymovies.dagger;

import android.content.SharedPreferences;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import br.com.wellingtoncosta.mymovies.BuildConfig;
import br.com.wellingtoncosta.mymovies.retrofit.AuthenticationService;
import br.com.wellingtoncosta.mymovies.retrofit.MovieService;
import br.com.wellingtoncosta.mymovies.retrofit.UserService;
import br.com.wellingtoncosta.mymovies.retrofit.interceptor.TokenInterceptor;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Wellington Costa on 30/04/17.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    TokenInterceptor provideTokenInterceptor(SharedPreferences preferences) {
        return new TokenInterceptor(preferences);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(TokenInterceptor tokenInterceptor) {
     return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(tokenInterceptor)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Provides
    @Singleton
    AuthenticationService provideAuthenticationService(Retrofit retrofit) {
        return retrofit.create(AuthenticationService.class);
    }

    @Provides
    @Singleton
    MovieService provideMovieService(Retrofit retrofit) {
        return retrofit.create(MovieService.class);
    }

}
