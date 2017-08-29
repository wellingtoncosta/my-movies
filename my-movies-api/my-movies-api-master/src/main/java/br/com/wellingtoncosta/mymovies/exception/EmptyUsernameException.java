package br.com.wellingtoncosta.mymovies.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Wellington Costa on 05/05/17.
 */
public class EmptyUsernameException extends AuthenticationException {

    public EmptyUsernameException() {
        super("Informe seu e-mail.");
    }

}