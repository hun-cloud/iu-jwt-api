package com.jwt.iu.exception;

public class TokenValidException extends RuntimeException{

    public TokenValidException(String message) {
        super(message);
    }
}
