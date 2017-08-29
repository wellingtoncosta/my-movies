package br.com.wellingtoncosta.mymovies.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;

import java.io.IOException;

import br.com.wellingtoncosta.mymovies.Application;
import br.com.wellingtoncosta.mymovies.R;
import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.exception.ErrorResponse;
import br.com.wellingtoncosta.mymovies.validation.Validation;
import br.com.wellingtoncosta.mymovies.validation.validators.EmailValidator;
import br.com.wellingtoncosta.mymovies.validation.validators.NotEmptyValidator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Wellington Costa on 26/04/17.
 */
public class LoginActivity extends SaveUserActivity {

    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;

    @BindView(R.id.emailField)
    EditText emailField;

    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;

    @BindView(R.id.passwordField)
    EditText passwordField;

    private Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Application) getApplication()).component().inject(LoginActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setupValidation();
    }

    private void setupValidation() {
        validation = new Validation();

        validation.addValidator(new EmailValidator(
                emailLayout,
                Patterns.EMAIL_ADDRESS.pattern(),
                getString(R.string.invalid_email)
        ));

        validation.addValidator(new NotEmptyValidator(
                passwordLayout,
                getString(R.string.field_required)
        ));
    }

    @OnEditorAction(R.id.passwordField)
    public boolean handleEditorAction() {
        login();
        return true;
    }

    @OnClick(R.id.loginButton)
    public void login() {
        hideKeyboard();

        if (validation.validate()) {
            User user = buildUser();
            final ProgressDialog progress = ProgressDialog.show(this, "Aguarde", "Efetuando o login...", true);
            progress.setCancelable(false);

            authenticationService.login(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        saveNewUserLocal(response.body(), progress);
                    } else {
                        progress.dismiss();
                        handleErrorResponse(response);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    progress.dismiss();
                    t.printStackTrace();
                    Log.e("LoginError", t.getMessage());
                    Snackbar.make(toolbar, R.string.server_communication_error, Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private void handleErrorResponse(Response<User> response) {
        try {
            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
            Snackbar.make(toolbar, errorResponse.getMessage(), Snackbar.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("exception", e.getMessage(), e);
        }
    }

    private User buildUser() {
        User user = new User();
        user.setEmail(emailField.getText().toString());

        try {
            byte[] bytes = passwordField.getText().toString().getBytes("UTF-8");
            String encryptedPassword = Base64.encodeToString(bytes, Base64.DEFAULT);
            user.setPassword(encryptedPassword);
        } catch (IOException e) {
            Log.e("exception", e.getMessage(), e);
        }

        return user;
    }

    @OnClick(R.id.registerNewUserButton)
    public void registerNewUser() {
        startActivity(new Intent(this, UserRegistrationActivity.class));
        finish();
    }

}
