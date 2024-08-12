# iu-jwt-api
## 프로젝트 개요
### 프로젝트 설명
- Jwt 토큰을 생성하고 검증할 수 있는 라이브러리입니다.
- ip 와 user-agnet 입력을 강제하여 보안을 강화합니다.

## 설치 방법 - 예시 (실제로 라이브러리 배포하지 않았습니다.)
Maven 또는 Gradle을 사용하여 라이브러리를 프로젝트에 추가할 수 있습니다
### Maven
```maven
<dependency>
  <groupId>com.jwt.iu</groupId>
  <artifactId>iu-jwt-api</artifactId>
  <version>1.0.0</version>
</dependency>
```
### Gradle
```gradle
implementation 'com.jwt.iu:iu-jwt-api:1.0.0'
```
## 사용법
### 토큰 생성
```java
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
```
### 토큰 유효성 검사
``` java
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
}
```
