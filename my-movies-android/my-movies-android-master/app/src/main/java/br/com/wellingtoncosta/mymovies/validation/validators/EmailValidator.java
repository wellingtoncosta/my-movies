package br.com.wellingtoncosta.mymovies.validation.validators;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * @author Wellington Costa on 01/05/17.
 */
public class EmailValidator extends Validator {

    public EmailValidator(TextInputLayout layout, String pattern, String errorMessage) {
        super(layout, pattern, errorMessage);
    }

    @Override
    public boolean validate() {
        boolean isValid = true;
        EditText editText = layout.getEditText();

        if (editText != null) {
            String value = editText.getText().toString();

            if (!pattern.isEmpty()) {
                boolean found = Pattern.matches(pattern, value);
                if (!found) {
                    setErrorLayout();
                    isValid = false;
                } else {
                    clearErrorLayout();
                }
            }
        }

        return isValid;
    }
}
