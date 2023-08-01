package com.accenture.challenge.exceptions;

public class ZipCodeInvalidException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ZipCodeInvalidException(String msg) {
        super(msg);
    }

}
