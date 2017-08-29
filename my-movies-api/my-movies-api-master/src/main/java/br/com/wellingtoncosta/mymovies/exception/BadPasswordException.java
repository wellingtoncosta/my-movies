package br.com.wellingtoncosta.mymovies.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Wellington Costa on 05/05/17.
 */
public class BadPasswordException extends AuthenticationException {

    public BadPasswordException() {
        super("Senha inválida.");
    }

}