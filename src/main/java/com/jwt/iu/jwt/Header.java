package com.jwt.iu.jwt;

public class Header {
    private SignatureAlgorithm alg = SignatureAlgorithm.HS256;
    private String typ = "JWT";
}
