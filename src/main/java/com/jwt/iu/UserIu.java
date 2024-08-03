package com.jwt.iu;

import java.util.HashMap;
import java.util.Map;

public class UserIu {
    private String ip;
    private String userAgent;

    public UserIu(String ip, String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("ip", ip);
        result.put("userAgent", userAgent);
        return result;
    }
}
