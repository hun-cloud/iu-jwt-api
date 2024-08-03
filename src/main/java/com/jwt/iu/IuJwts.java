package com.jwt.iu;

import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Classes;

import java.util.Map;

public class IuJwts {
    private static final Class[] MAP_ARG = new Class[]{Map.class};

    private IuJwts() {
    }

    public static Header header() {
        return (Header) Classes.newInstance("io.jsonwebtoken.impl.DefaultHeader");
    }

    public static Header header(Map<String, Object> header) {
        return (Header)Classes.newInstance("io.jsonwebtoken.impl.DefaultHeader", MAP_ARG, new Object[]{header});
    }

    public static JwsHeader jwsHeader() {
        return (JwsHeader)Classes.newInstance("io.jsonwebtoken.impl.DefaultJwsHeader");
    }

    public static JwsHeader jwsHeader(Map<String, Object> header) {
        return (JwsHeader)Classes.newInstance("io.jsonwebtoken.impl.DefaultJwsHeader", MAP_ARG, new Object[]{header});
    }

    public static Claims claims() {
        return (Claims)Classes.newInstance("io.jsonwebtoken.impl.DefaultClaims");
    }

    public static Claims claims(Map<String, Object> claims) {
        return (Claims)Classes.newInstance("io.jsonwebtoken.impl.DefaultClaims", MAP_ARG, new Object[]{claims});
    }

    /** @deprecated */
    @Deprecated
    public static JwtParser parser() {
        return (JwtParser)Classes.newInstance("io.jsonwebtoken.impl.DefaultJwtParser");
    }

    public static JwtParserBuilder parserBuilder() {
        return (JwtParserBuilder)Classes.newInstance("io.jsonwebtoken.impl.DefaultJwtParserBuilder");
    }

    public static IuJwtBuilder builder() {
        return (IuJwtBuilder)Classes.newInstance("com.jwt.iu.IuDefaultJwtBuilder");
    }
}
