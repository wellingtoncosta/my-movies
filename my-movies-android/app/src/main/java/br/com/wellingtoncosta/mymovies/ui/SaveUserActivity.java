package br.com.wellingtoncosta.mymovies.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import javax.inject.Inject;

import br.com.wellingtoncosta.mymovies.Application;
import br.com.wellingtoncosta.mymovies.R;
import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.retrofit.AuthenticationService;
import butterknife.BindView;
import io.realm.Realm;

/**
 * @author Wellington Costa on 06/05/17.
 */

public class SaveUserActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Inject
    AuthenticationService authenticationService;

    @Inject
    Gson gson;

    @Inject
    Realm realm;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ((Application) getApplication()).component().inject(SaveUserActivity.this);
    }

    void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    void saveNewUserLocal(final User user, final ProgressDialog progress) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                User newUser = bgRealm.createObject(User.class, user.getId());
                newUser.setName(user.getName());
                newUser.setEmail(user.getEmail());
                newUser.setPassword(user.getPassword());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                progress.dismiss();
                finish();
                startActivity(new Intent(SaveUserActivity.this, MoviesActivity.class));
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                progress.dismiss();
                Log.e("exception", error.getMessage(), error);
                Snackbar.make(toolbar, R.string.save_data_local_failure, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
