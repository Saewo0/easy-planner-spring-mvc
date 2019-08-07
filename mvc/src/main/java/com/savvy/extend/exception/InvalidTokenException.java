package com.savvy.extend.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String msg) {
        super(msg);
    }

    public InvalidTokenException(String msg, Exception cause) {
        super(msg + " caused by " + cause.getMessage());
    }
}
