package com.jwt.iu.jwt;

import org.junit.Assert;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class Claim {
    // 토큰 발행 주체 (site)
    private String issuer = "iss";
    // 토큰의 주제 - 식별자
    private String subject = "sub";
    // 토큰의 수신자 (site)
    private String audience = "aud";
    // 토큰 생성시간
    private String issuedAt = "iat";
    // 토큰의 만료시간
    private String expiration = "exp";
    // IP
    private String ip;
    // userAgent
    private String userAgent;

    public Claim(String issuer, String subject, String audience, Date issuedAt, Date expiration, String ip, String userAgent) {
        assertNotNull(issuer);
        assertNotNull(subject);
        assertNotNull(audience);
        assertNotNull(expiration);
        assertNotNull(ip);
        assertNotNull(userAgent);
        this.issuer = issuer;
        this.subject = subject;
        this.audience = audience;
        this.issuedAt = String.valueOf((issuedAt.getTime() / 1000L));
        this.expiration = String.valueOf((expiration.getTime() / 1000L));
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getSubject() {
        return subject;
    }

    public String getAudience() {
        return audience;
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
