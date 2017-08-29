package br.com.wellingtoncosta.mymovies.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.facebook.imagepipeline.core.ImagePipelineConfig;

import javax.inject.Singleton;

import br.com.wellingtoncosta.mymovies.Application;

import dagger.Module;
import dagger.Provides;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Wellington Costa on 01/05/17.
 */
@Module
public class AppModule {

    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        Realm.init(application);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return application.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    ImagePipelineConfig provideImagePipelineConfig () {
        return ImagePipelineConfig.newBuilder(application)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .build();
    }

}