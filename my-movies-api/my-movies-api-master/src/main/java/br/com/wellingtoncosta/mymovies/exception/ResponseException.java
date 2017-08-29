package br.com.wellingtoncosta.mymovies.exception;

import lombok.Builder;

/**
 * @author Wellington Costa on 01/05/17.
 */
@Builder
public class ResponseException {

    private int code;

    private String message;

    private String url;
}
