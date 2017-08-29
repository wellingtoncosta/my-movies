package br.com.wellingtoncosta.mymovies.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Wellington Costa on 03/05/17.
 */
public class UserNotFoundException extends AuthenticationException {

    public UserNotFoundException() {
        super("Usuário não encontrado.");
    }

}
