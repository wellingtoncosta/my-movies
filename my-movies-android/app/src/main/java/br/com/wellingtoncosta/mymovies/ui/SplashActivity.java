package br.com.wellingtoncosta.mymovies.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import br.com.wellingtoncosta.mymovies.Application;
import br.com.wellingtoncosta.mymovies.R;
import br.com.wellingtoncosta.mymovies.domain.User;
import io.realm.Realm;

/**
 * @author Wellington Costa on 26/04/17.
 */
public class SplashActivity extends Activity {

    @Inject
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Application) getApplication()).component().inject(SplashActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showNextActivity();
    }

    private void showNextActivity() {
        Intent intent = new Intent();

        if (hasUser()) {
            intent.setClass(this, MoviesActivity.class);
        } else {
            intent.setClass(this, LoginActivity.class);
        }

        startActivity(intent);
        finish();

    }

    private boolean hasUser() {
        return !realm.where(User.class).findAll().isEmpty();
    }

}
