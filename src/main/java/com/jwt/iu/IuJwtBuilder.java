package com.jwt.iu;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.Serializer;
import io.jsonwebtoken.security.InvalidKeyException;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public interface IuJwtBuilder extends JwtBuilder {
    IuJwtBuilder setUserIu(UserIu userIu);

    IuJwtBuilder setHeader(Header var1);

    IuJwtBuilder setHeader(Map<String, Object> var1);

    IuJwtBuilder setHeaderParams(Map<String, Object> var1);

    IuJwtBuilder setHeaderParam(String var1, Object var2);

    IuJwtBuilder setPayload(String var1);

    IuJwtBuilder setClaims(Claims var1);

    IuJwtBuilder setClaims(Map<String, ?> var1);

    IuJwtBuilder addClaims(Map<String, Object> var1);

    IuJwtBuilder setIssuer(String var1);

    IuJwtBuilder setSubject(String var1);

    IuJwtBuilder setAudience(String var1);

    IuJwtBuilder setExpiration(Date var1);

    IuJwtBuilder setNotBefore(Date var1);

    IuJwtBuilder setIssuedAt(Date var1);

    IuJwtBuilder setId(String var1);

    IuJwtBuilder claim(String var1, Object var2);

    IuJwtBuilder signWith(Key var1) throws InvalidKeyException;

    /** @deprecated */
    @Deprecated
    IuJwtBuilder signWith(SignatureAlgorithm var1, byte[] var2) throws InvalidKeyException;

    /** @deprecated */
    @Deprecated
    IuJwtBuilder signWith(SignatureAlgorithm var1, String var2) throws InvalidKeyException;

    /** @deprecated */
    @Deprecated
    IuJwtBuilder signWith(SignatureAlgorithm var1, Key var2) throws InvalidKeyException;

    IuJwtBuilder signWith(Key var1, SignatureAlgorithm var2) throws InvalidKeyException;

    IuJwtBuilder compressWith(CompressionCodec var1);

    IuJwtBuilder base64UrlEncodeWith(Encoder<byte[], String> var1);

    IuJwtBuilder serializeToJsonWith(Serializer<Map<String, ?>> var1);

    String compact();
}
