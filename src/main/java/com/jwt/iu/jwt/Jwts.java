package com.jwt.iu.jwt;

import static org.junit.Assert.assertNotNull;

public class Jwts {
    private Header header;
    private Claim claim;
    private String key;

    public Jwts(Header header, Claim claim, String key) {
        assertNotNull("Header can't be null", header);
        assertNotNull("claim can't be null", claim);
        assertNotNull("key can't be null", key);
        this.header = header;
        this.claim = claim;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Header getHeader() {
        return header;
    }

    public Claim getClaim() {
        return claim;
    }
}
