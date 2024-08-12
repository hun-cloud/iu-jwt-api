package com.jwt.iu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.iu.exception.TokenValidException;
import com.jwt.iu.jwt.Claim;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

public class IuJwtValidatorImpl implements IuJwtValidator {
    @Override
    public boolean validateToken(String token, String key) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();

            String[] tokenArr = token.split("\\.");

            if (tokenArr.length != 3) {
                throw new TokenValidException("Invalid token.");
            }
            String header = tokenArr[0];
            String payload = tokenArr[1];
            String signature = tokenArr[2];

            // String headerJson = base64ToStr(header);
            String payloadJson = base64ToStr(payload);
            String data = header + "." + payload;
            String computedSignature = hmacSha256(data, key);
            if (!signature.equals(computedSignature)) {
                throw new TokenValidException("Invalid token.");
            }

            Claim claim = objectMapper.readValue(payloadJson, Claim.class);
            String expiration = claim.getExpiration();

            if (new Date().getTime() - Long.valueOf(expiration) <= 0) {
                throw new TokenValidException("Invalid token - The expiration date has expired.");
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new TokenValidException("Invalid token.");
        }
    }

    private String hmacSha256(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256Hmac.init(keySpec);
        byte[] macData = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getUrlEncoder().withoutPadding().encodeToString(macData);
    }

    private String base64ToStr(String str) {
        str = str.replaceAll("\\s", "");
        return new String(Base64.getDecoder().decode(str.getBytes()), StandardCharsets.UTF_8);
    }
}
