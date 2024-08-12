package com.jwt.iu;

import com.jwt.iu.jwt.Claim;
import com.jwt.iu.jwt.Header;
import com.jwt.iu.jwt.Jwts;
import com.jwt.iu.jwt.SignatureAlgorithm;

import java.time.Duration;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws Exception{
        // create token
        String secretKey = "afskjmfasiknmoifenvxcoiknvoindlakfneslknfslkdnvoihnxcvionernlkasfndoivhnosinvi";

        String token = createToken(secretKey);

        IuJwtValidator iuJwtValidator = new IuJwtValidatorImpl();

        boolean result = iuJwtValidator.validateToken(token, secretKey);

        if (result == true) {
            // 유효한 토큰입니다.
            System.out.println("유효한 토큰입니다.");
        } else if (result == false) {
            // 유효하지 않은 토큰입니다.
            System.out.println("유효하지 않은 토큰입니다.");
        }
    }

    public static String createToken(String secretKey) throws Exception{
        String id = "abc123";
        Date date = new Date();
        long tokenValidTime = Duration.ofDays(10).toMillis();
        String ip = "123.123.123.123";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36";

        Header header = new Header(SignatureAlgorithm.HS256, "JWT");
        Claim claim = new Claim(id, date, new Date(date.getTime() + tokenValidTime), ip, userAgent);

        Jwts jwts = new Jwts(header, claim, secretKey);
        // jwt 토큰 생성
        String tokenStr = IuJwtCreator.create(jwts);

        return tokenStr;
    }
}
