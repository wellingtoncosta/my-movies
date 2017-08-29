package br.com.wellingtoncosta.mymovies.validation.validators;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * @author Wellington Costa on 01/05/17.
 */
public class PasswordValidatior extends Validator {

    private TextInputLayout confirmPasswordLayout;

    private String confirmPasswordErrorMessage;

    public PasswordValidatior(
            TextInputLayout passwordLayout,
            TextInputLayout confirmPasswordLayout,
            String passwordErrorMessage,
            String confirmPasswordErrorMessage) {

        super(passwordLayout, passwordErrorMessage);
        this.confirmPasswordLayout = confirmPasswordLayout;
        this.confirmPasswordErrorMessage = confirmPasswordErrorMessage;
    }

    @Override
    public boolean validate() {
        boolean isValid = true;
        EditText passwordField = layout.getEditText();
        EditText confirmPasswordField = confirmPasswordLayout.getEditText();

        if (passwordField != null && confirmPasswordField != null) {
            String password = passwordField.getText().toString();
            String confirmPassword = confirmPasswordField.getText().toString();

            if (password.isEmpty()) {
                setErrorLayout();
                isValid = false;
            } else {
                clearErrorLayout();
                clearErrorLayout(confirmPasswordLayout);
            }

            if (confirmPassword.isEmpty()) {
                setErrorLayout(confirmPasswordLayout, errorMessage);
                isValid = false;
            } else if (!password.equals(confirmPassword)) {
                setErrorLayout(confirmPasswordLayout, confirmPasswordErrorMessage);
                isValid = false;
            } else {
                clearErrorLayout();
                clearErrorLayout(confirmPasswordLayout);
            }
        }

        return isValid;
    }

}
