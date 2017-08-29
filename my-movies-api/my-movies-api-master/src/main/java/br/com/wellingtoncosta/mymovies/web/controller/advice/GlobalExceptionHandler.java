package br.com.wellingtoncosta.mymovies.web.controller.advice;

import br.com.wellingtoncosta.mymovies.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Wellington Costa on 01/05/17.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorResponse handleExpiredJwtException(Exception e) {
        log.error("ExpiredJwtException", e);
        return ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .message("Seu acesso expirou.")
                .build();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse handleUserAlreadyExistsException(Exception e) {
        log.error("UserAlreadyExistsException", e);
        return ErrorResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .message("Usuário já existe. Verifique os dados e tente novamente.")
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    public ErrorResponse handleAuthenticationException(Exception e) {
        log.error("UserAlreadyExistsException", e);
        return ErrorResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .message(e.getMessage())
                .build();
    }

    /*@ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(Exception e) {
        log.error("UserNotFoundException", e);
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message("Usuário não encontrado. Verifique os dados e tente novamente.")
                .build();
    }

    @ExceptionHandler(EmptyUsernameException.class)
    public ErrorResponse handleEmptyUsernameException(Exception e) {
        log.error("EmptyUsernameException", e);
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Informe seu e-mail.")
                .build();
    }

    @ExceptionHandler(EmptyPasswordException.class)
    public ErrorResponse handleEmptyPasswordException(Exception e) {
        log.error("EmptyPasswordException", e);
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Informe sua senha.")
                .build();
    }

    @ExceptionHandler(BadPasswordException.class)
    public ErrorResponse handleBadPasswordException(Exception e) {
        log.error("BadPasswordException", e);
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Senha inválida.")
                .build();
    }*/

    @ExceptionHandler(NullPointerException.class)
    public ErrorResponse handleNullPointerException(Exception e) {
        log.error("NullPointerException", e);
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Não foi possível acessar informação. Tente novamente.")
                .build();
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error("Exception", e);
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Não foi possível realizar a ação. Tente novamente.")
                .build();
    }


    @Builder
    private static class ErrorResponse {
        private int code;
        private String message;
    }

}
