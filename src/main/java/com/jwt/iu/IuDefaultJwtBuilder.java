package com.jwt.iu;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.crypto.DefaultJwtSigner;
import io.jsonwebtoken.impl.crypto.JwtSigner;
import io.jsonwebtoken.impl.lang.LegacyServices;
import io.jsonwebtoken.io.*;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.InvalidKeyException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class IuDefaultJwtBuilder implements IuJwtBuilder {

    private Header header;
    private Claims claims;
    private String payload;
    private SignatureAlgorithm algorithm;
    private Key key;
    private Serializer<Map<String, ?>> serializer;
    private Encoder<byte[], String> base64UrlEncoder;
    private CompressionCodec compressionCodec;
    private UserIu userIu;

    public IuDefaultJwtBuilder() {
        this.base64UrlEncoder = Encoders.BASE64URL;
    }

    public IuJwtBuilder setUserIu(UserIu userIu) {
        Assert.notNull(userIu, "userIu cannot be null.");
        Assert.notNull(userIu.getIp(), "userIu ip cannot be null.");
        Assert.notNull(userIu.getUserAgent(), "userIu userAgent cannot be null.");
        this.userIu = userIu;
        addClaims(userIu.toMap());
        return this;
    }

    public IuJwtBuilder serializeToJsonWith(Serializer<Map<String, ?>> serializer) {
        Assert.notNull(serializer, "Serializer cannot be null.");
        this.serializer = serializer;
        return this;
    }

    public IuJwtBuilder base64UrlEncodeWith(Encoder<byte[], String> base64UrlEncoder) {
        Assert.notNull(base64UrlEncoder, "base64UrlEncoder cannot be null.");
        this.base64UrlEncoder = base64UrlEncoder;
        return this;
    }

    public IuJwtBuilder setHeader(Header header) {
        this.header = header;
        return this;
    }

    public IuJwtBuilder setHeader(Map<String, Object> header) {
        this.header = new DefaultHeader(header);
        return this;
    }

    public IuJwtBuilder setHeaderParams(Map<String, Object> params) {
        if (!Collections.isEmpty(params)) {
            Header header = this.ensureHeader();
            Iterator i$ = params.entrySet().iterator();

            while(i$.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)i$.next();
                header.put(entry.getKey(), entry.getValue());
            }
        }

        return this;
    }

    protected Header ensureHeader() {
        if (this.header == null) {
            this.header = new DefaultHeader();
        }

        return this.header;
    }

    public IuJwtBuilder setHeaderParam(String name, Object value) {
        this.ensureHeader().put(name, value);
        return this;
    }

    public IuJwtBuilder signWith(Key key) throws InvalidKeyException {
        Assert.notNull(key, "Key argument cannot be null.");
        SignatureAlgorithm alg = SignatureAlgorithm.forSigningKey(key);
        return this.signWith(key, alg);
    }

    public IuJwtBuilder signWith(Key key, SignatureAlgorithm alg) throws InvalidKeyException {
        Assert.notNull(key, "Key argument cannot be null.");
        Assert.notNull(alg, "SignatureAlgorithm cannot be null.");
        alg.assertValidSigningKey(key);
        this.createSigner(alg, key);
        this.algorithm = alg;
        this.key = key;
        return this;
    }

    public IuJwtBuilder signWith(SignatureAlgorithm alg, byte[] secretKeyBytes) throws InvalidKeyException {
        Assert.notNull(alg, "SignatureAlgorithm cannot be null.");
        Assert.notEmpty(secretKeyBytes, "secret key byte array cannot be null or empty.");
        Assert.isTrue(alg.isHmac(), "Key bytes may only be specified for HMAC signatures.  If using RSA or Elliptic Curve, use the signWith(SignatureAlgorithm, Key) method instead.");
        SecretKey key = new SecretKeySpec(secretKeyBytes, alg.getJcaName());
        return this.signWith((Key)key, (SignatureAlgorithm)alg);
    }

    public IuJwtBuilder signWith(SignatureAlgorithm alg, String base64EncodedSecretKey) throws InvalidKeyException {
        Assert.hasText(base64EncodedSecretKey, "base64-encoded secret key cannot be null or empty.");
        Assert.isTrue(alg.isHmac(), "Base64-encoded key bytes may only be specified for HMAC signatures.  If using RSA or Elliptic Curve, use the signWith(SignatureAlgorithm, Key) method instead.");
        byte[] bytes = (byte[]) Decoders.BASE64.decode(base64EncodedSecretKey);
        return this.signWith(alg, bytes);
    }

    public IuJwtBuilder signWith(SignatureAlgorithm alg, Key key) {
        return this.signWith(key, alg);
    }

    public IuJwtBuilder compressWith(CompressionCodec compressionCodec) {
        Assert.notNull(compressionCodec, "compressionCodec cannot be null");
        this.compressionCodec = compressionCodec;
        return this;
    }

    public IuJwtBuilder setPayload(String payload) {
        this.payload = payload;
        return this;
    }

    protected Claims ensureClaims() {
        if (this.claims == null) {
            this.claims = new DefaultClaims();
        }

        return this.claims;
    }

    public IuJwtBuilder setClaims(Claims claims) {
        this.claims = claims;
        return this;
    }

    public IuJwtBuilder setClaims(Map<String, ?> claims) {
        this.claims = new DefaultClaims(claims);
        return this;
    }

    public IuJwtBuilder addClaims(Map<String, Object> claims) {
        this.ensureClaims().putAll(claims);
        return this;
    }

    public IuJwtBuilder setIssuer(String iss) {
        if (Strings.hasText(iss)) {
            this.ensureClaims().setIssuer(iss);
        } else if (this.claims != null) {
            this.claims.setIssuer(iss);
        }

        return this;
    }

    public IuJwtBuilder setSubject(String sub) {
        if (Strings.hasText(sub)) {
            this.ensureClaims().setSubject(sub);
        } else if (this.claims != null) {
            this.claims.setSubject(sub);
        }

        return this;
    }

    public IuJwtBuilder setAudience(String aud) {
        if (Strings.hasText(aud)) {
            this.ensureClaims().setAudience(aud);
        } else if (this.claims != null) {
            this.claims.setAudience(aud);
        }

        return this;
    }

    public IuJwtBuilder setExpiration(Date exp) {
        if (exp != null) {
            this.ensureClaims().setExpiration(exp);
        } else if (this.claims != null) {
            this.claims.setExpiration(exp);
        }

        return this;
    }

    public IuJwtBuilder setNotBefore(Date nbf) {
        if (nbf != null) {
            this.ensureClaims().setNotBefore(nbf);
        } else if (this.claims != null) {
            this.claims.setNotBefore(nbf);
        }

        return this;
    }

    public IuJwtBuilder setIssuedAt(Date iat) {
        if (iat != null) {
            this.ensureClaims().setIssuedAt(iat);
        } else if (this.claims != null) {
            this.claims.setIssuedAt(iat);
        }

        return this;
    }

    public IuJwtBuilder setId(String jti) {
        if (Strings.hasText(jti)) {
            this.ensureClaims().setId(jti);
        } else if (this.claims != null) {
            this.claims.setId(jti);
        }

        return this;
    }

    public IuJwtBuilder claim(String name, Object value) {
        Assert.hasText(name, "Claim property name cannot be null or empty.");
        if (this.claims == null) {
            if (value != null) {
                this.ensureClaims().put(name, value);
            }
        } else if (value == null) {
            this.claims.remove(name);
        } else {
            this.claims.put(name, value);
        }

        return this;
    }

    public String compact() {
        if (this.serializer == null) {
            this.serializer = (Serializer) LegacyServices.loadFirst(Serializer.class);
        }

        if (this.userIu == null) {
            throw new IllegalStateException("userIu is a required value.");
        }

        if (this.payload == null && Collections.isEmpty(this.claims)) {
            this.payload = "";
        }

        if (this.payload != null && !Collections.isEmpty(this.claims)) {
            throw new IllegalStateException("Both 'payload' and 'claims' cannot both be specified. Choose either one.");
        } else {
            Header header = this.ensureHeader();
            Object jwsHeader;
            if (header instanceof JwsHeader) {
                jwsHeader = (JwsHeader)header;
            } else {
                jwsHeader = new DefaultJwsHeader(header);
            }

            if (this.key != null) {
                ((JwsHeader)jwsHeader).setAlgorithm(this.algorithm.getValue());
            } else {
                ((JwsHeader)jwsHeader).setAlgorithm(SignatureAlgorithm.NONE.getValue());
            }

            if (this.compressionCodec != null) {
                ((JwsHeader)jwsHeader).setCompressionAlgorithm(this.compressionCodec.getAlgorithmName());
            }

            String base64UrlEncodedHeader = this.base64UrlEncode(jwsHeader, "Unable to serialize header to json.");

            byte[] bytes;
            try {
                bytes = this.payload != null ? this.payload.getBytes(Strings.UTF_8) : this.toJson(this.claims);
            } catch (SerializationException var9) {
                SerializationException e = var9;
                throw new IllegalArgumentException("Unable to serialize claims object to json: " + e.getMessage(), e);
            }

            if (this.compressionCodec != null) {
                bytes = this.compressionCodec.compress(bytes);
            }

            String base64UrlEncodedBody = (String)this.base64UrlEncoder.encode(bytes);
            String jwt = base64UrlEncodedHeader + '.' + base64UrlEncodedBody;
            if (this.key != null) {
                JwtSigner signer = this.createSigner(this.algorithm, this.key);
                String base64UrlSignature = signer.sign(jwt);
                jwt = jwt + '.' + base64UrlSignature;
            } else {
                jwt = jwt + '.';
            }

            return jwt;
        }
    }

    protected JwtSigner createSigner(SignatureAlgorithm alg, Key key) {
        return new DefaultJwtSigner(alg, key, this.base64UrlEncoder);
    }

    /** @deprecated */
    @Deprecated
    protected String base64UrlEncode(Object o, String errMsg) {
        Assert.isInstanceOf(Map.class, o, "object argument must be a map.");
        Map m = (Map)o;

        byte[] bytes;
        try {
            bytes = this.toJson(m);
        } catch (SerializationException var6) {
            SerializationException e = var6;
            throw new IllegalStateException(errMsg, e);
        }

        return (String)this.base64UrlEncoder.encode(bytes);
    }

    /** @deprecated */
    @Deprecated
    protected byte[] toJson(Object object) throws SerializationException {
        Assert.isInstanceOf(Map.class, object, "object argument must be a map.");
        Map m = (Map)object;
        return this.serializer.serialize(m);
    }
}
