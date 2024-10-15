package com.sparta.springtrello.common.exception;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
