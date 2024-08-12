package com.jwt.iu.jwt;

import org.junit.Assert;

import static org.junit.Assert.assertNotNull;

public class Header {
    private SignatureAlgorithm alg = SignatureAlgorithm.HS256;
    private String typ = "JWT";

    public Header(SignatureAlgorithm alg, String typ) {
        assertNotNull("SignatureAlgorithm is not null", alg);
        assertNotNull("type is not null", typ);
        this.alg = alg;
        this.typ = typ;
    }

    public SignatureAlgorithm getAlg() {
        return alg;
    }

    public String getTyp() {
        return typ;
    }
}
