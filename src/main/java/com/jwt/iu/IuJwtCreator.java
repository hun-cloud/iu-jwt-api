package com.jwt.iu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.iu.jwt.Claim;
import com.jwt.iu.jwt.Header;
import com.jwt.iu.jwt.Jwts;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class IuJwtCreator {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String create(Jwts jwts) throws Exception{
        Header header = jwts.getHeader();
        Claim claim = jwts.getClaim();
        String headerStr = toBase64(objectMapper.writeValueAsString(header));
        String claimStr = toBase64(objectMapper.writeValueAsString(claim));
        String dataToSign = headerStr + "." + claimStr;

        String key = jwts.getKey();
        String signature = signWithHmacSHA256(dataToSign, key);

        return dataToSign + "." + signature;
    }

    private static String signWithHmacSHA256(String data, String key) throws Exception{
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        sha256Hmac.init(secretKey);
        byte[] signedBytes = sha256Hmac.doFinal(data.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(signedBytes);
    }

    private static String toBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
}
