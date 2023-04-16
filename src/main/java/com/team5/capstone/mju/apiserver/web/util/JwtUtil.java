package com.team5.capstone.mju.apiserver.web.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${extends.jwt-secret}")
    private String secret;

    private final String type = "JWT";
    private final String algorithm = "HS256";

    public String createJwt(Long userId, LocalDateTime expiredAt) {

        // 헤더 구성
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", type);
        headers.put("alg", algorithm);

        // Deprecated 된 signWith(SignatureAlgorithm.HS256, secret.getBytes()) 대신 권장하는 방법
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

        Date expiration = Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant());

        // payload 구성, sign 및 jwt 빌드
        return Jwts.builder()
                .setHeader(headers)
                .setSubject(String.valueOf(userId)) // subject를 userId로 취급
                .setExpiration(expiration)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

}
