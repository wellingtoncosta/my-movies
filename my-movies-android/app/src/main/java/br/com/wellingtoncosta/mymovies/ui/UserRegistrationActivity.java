package br.com.wellingtoncosta.mymovies.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import javax.inject.Inject;

import br.com.wellingtoncosta.mymovies.Application;
import br.com.wellingtoncosta.mymovies.R;
import br.com.wellingtoncosta.mymovies.domain.User;
import br.com.wellingtoncosta.mymovies.exception.ErrorResponse;
import br.com.wellingtoncosta.mymovies.retrofit.UserService;
import br.com.wellingtoncosta.mymovies.validation.Validation;
import br.com.wellingtoncosta.mymovies.validation.validators.EmailValidator;
import br.com.wellingtoncosta.mymovies.validation.validators.NotEmptyValidator;
import br.com.wellingtoncosta.mymovies.validation.validators.PasswordValidatior;
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
public class UserRegistrationActivity extends SaveUserActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.nameLayout)
    TextInputLayout nameLayout;

    @BindView(R.id.emailLayout)
    TextInputLayout emailLayout;

    @BindView(R.id.passwordLayout)
    TextInputLayout passwordLayout;

    @BindView(R.id.confirmPasswordLayout)
    TextInputLayout confirmPasswordLayout;

    @BindView(R.id.nameField)
    EditText nameField;

    @BindView(R.id.emailField)
    EditText emailField;

    @BindView(R.id.passwordField)
    EditText passwordField;

    @Inject
    UserService userService;

    private Validation validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((Application) getApplication()).component().inject(UserRegistrationActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        setupToolbar();
        setupValidation();
    }

    @Override
    public void onBackPressed() {
        goToLoginActivity();
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });
    }

    private void goToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void setupValidation() {
        validation = new Validation();

        validation.addValidator(new NotEmptyValidator(
                nameLayout,
                getString(R.string.field_required)
        ));

        validation.addValidator(new EmailValidator(
                emailLayout,
                Patterns.EMAIL_ADDRESS.pattern(),
                getString(R.string.invalid_email)
        ));

        validation.addValidator(new PasswordValidatior(
                passwordLayout,
                confirmPasswordLayout,
                getString(R.string.field_required),
                getString(R.string.different_password)
        ));
    }

    @OnEditorAction(R.id.confirmPasswordField)
    public boolean handleEditorAction() {
        saveNewUser();
        return true;
    }

    @OnClick(R.id.saveNewUserButton)
    public void saveNewUser() {
        hideKeyboard();

        if (validation.validate()) {
            User user = buildNewUser();
            final ProgressDialog progress = ProgressDialog.show(this, "Aguarde", "Salvando seus dados...", true);
            progress.setCancelable(false);

            userService.saveNewUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        saveNewUserLocal(response.body(), progress);
                    }
                    else if (response.code() == 409) {
                        progress.dismiss();
                        handleUserAlreadyExistsException(response);
                    } else {
                        progress.dismiss();
                        Snackbar.make(toolbar, R.string.server_communication_error, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    progress.dismiss();
                    t.printStackTrace();
                    Log.e("UserRegistrationError", t.getMessage());
                    Snackbar.make(toolbar, R.string.server_communication_error, Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private User buildNewUser() {
        User user = new User();
        user.setName(nameField.getText().toString());
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

    private void handleUserAlreadyExistsException(Response<User> response) {
        try {
            ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
            Snackbar.make(toolbar, errorResponse.getMessage(), Snackbar.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("exception", e.getMessage(), e);
        }
    }

}
