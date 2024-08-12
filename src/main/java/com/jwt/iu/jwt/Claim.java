package com.jwt.iu.jwt;

import org.junit.Assert;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class Claim {
    // 토큰의 주제 - 식별자
    private String subject = "sub";
    // 토큰 생성시간
    private String issuedAt = "iat";
    // 토큰의 만료시간
    private String expiration = "exp";
    // IP
    private String ip;
    // userAgent
    private String userAgent;

    public Claim() {
    }

    public Claim(String subject, Date issuedAt, Date expiration, String ip, String userAgent) {
        assertNotNull(subject);
        assertNotNull(expiration);
        assertNotNull(ip);
        assertNotNull(userAgent);
        this.subject = subject;
        this.issuedAt = String.valueOf((issuedAt.getTime() / 1000L));
        this.expiration = String.valueOf((expiration.getTime() / 1000L));
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getSubject() {
        return subject;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
