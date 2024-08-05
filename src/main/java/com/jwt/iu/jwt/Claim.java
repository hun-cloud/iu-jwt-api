package com.jwt.iu.jwt;

public class Claim {
    // 토큰 발행 주체 (site)
    private String issuer = "iss";
    // 토큰의 주제 - 식별자
    private String subject = "sub";
    // 토큰의 수신자 (site)
    private String audience = "aud";
    // 토큰의 만료시간
    private String expiration = "exp";
    // IP
    private String ip;
    // userAgent
    private String userAgent;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
