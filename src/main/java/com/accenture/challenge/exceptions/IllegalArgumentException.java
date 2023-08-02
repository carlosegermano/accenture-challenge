package com.accenture.challenge.exceptions;

public class IllegalArgumentException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IllegalArgumentException(String msg) {
        super(msg);
    }

}