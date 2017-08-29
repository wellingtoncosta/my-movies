package br.com.wellingtoncosta.mymovies.validation;

import java.util.ArrayList;
import java.util.List;

import br.com.wellingtoncosta.mymovies.validation.validators.Validator;

/**
 * @author Wellington Costa on 01/05/17.
 *
 * TODO create an annotation-based library
 */
public class Validation {

    private List<Validator> validators;
    private List<Boolean> results = new ArrayList<>();

    public Validation() {
        this.validators = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    public void addValidator(Validator validator) {
        validators.add(validator);
    }

    public boolean validate() {
        boolean valid = true;
        validateFields();

        for (Boolean isValid : results) {
            if (!isValid) {
                valid = isValid;
                break;
            }
        }

        return valid;
    }

    private void validateFields() {
        results.clear();

        for (Validator validator : validators) {
            results.add(validator.validate());
        }
    }
}
