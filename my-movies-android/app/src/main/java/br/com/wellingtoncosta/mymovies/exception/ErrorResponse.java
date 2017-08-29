package br.com.wellingtoncosta.mymovies.exception;

import android.util.Log;

import okhttp3.ResponseBody;

/**
 * @author Wellington Costa on 01/05/17.
 */

public class ErrorResponse {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        if (message.contains(":")) {
            int index = message.indexOf(":");
            return message.substring(index +2);
        }

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
