package br.com.wellingtoncosta.mymovies.validation.validators;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * @author Wellington Costa on 01/05/17.
 */
public class NotEmptyValidator extends Validator {

    public NotEmptyValidator(TextInputLayout layout, String message) {
        super(layout, message);
    }

    @Override
    public boolean validate() {
        boolean isValid = true;
        EditText editText = layout.getEditText();

        if (editText != null) {
            String value = editText.getText().toString();

            if (value.isEmpty()) {
                setErrorLayout();
                isValid = false;
            } else {
                clearErrorLayout();
            }
        }

        return isValid;
    }
}
