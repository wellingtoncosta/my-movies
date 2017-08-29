package br.com.wellingtoncosta.mymovies.validation.validators;

import android.support.design.widget.TextInputLayout;

/**
 * @author Wellington Costa on 01/05/17.
 */
public abstract class Validator {

    TextInputLayout layout;

    String pattern;

    String errorMessage;


    Validator(TextInputLayout layout, String pattern, String errorMessage) {
        this.layout = layout;
        this.pattern = pattern;
        this.errorMessage = errorMessage;
    }

    Validator(TextInputLayout layout, String errorMessage) {
        this.layout = layout;
        this.pattern = "";
        this.errorMessage = errorMessage;
    }

    public abstract boolean validate();

    void setErrorLayout() {
        layout.setErrorEnabled(true);
        layout.setError(errorMessage);
    }

    void setErrorLayout(TextInputLayout layout, String errorMessage) {
        layout.setErrorEnabled(true);
        layout.setError(errorMessage);
    }

    void clearErrorLayout() {
        layout.setErrorEnabled(false);
        layout.setError(null);
    }

    void clearErrorLayout(TextInputLayout layout) {
        layout.setErrorEnabled(false);
        layout.setError(null);
    }
}