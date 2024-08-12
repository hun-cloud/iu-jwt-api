package com.jwt.iu;

public interface IuJwtValidator {
    boolean validateToken(String token, String key);
}
