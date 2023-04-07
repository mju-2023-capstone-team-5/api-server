package com.team5.capstone.mju.apiserver.web.util;

import com.team5.capstone.mju.apiserver.web.vo.JwtPayloadParam;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${extends.jwt.secret}")
    private String secret;

    private final String type = "JWT";
    private final String algorithm = "HS256";

    public String createJwt(JwtPayloadParam jwtPayloadParam) {

        // 헤더 구성
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", type);
        headers.put("alg", algorithm);

        // Deprecated 된 signWith(SignatureAlgorithm.HS256, secret.getBytes()) 대신 권장하는 방법
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = Keys.hmacShaKeyFor(apiKeySecretBytes);

        // payload 구성, sign 및 jwt 빌드
        return Jwts.builder()
                .setHeader(headers)
                .setSubject(jwtPayloadParam.getUserId())
                .setExpiration(jwtPayloadParam.expirationDate())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidToken(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(jwt);
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public Long loadUser(String jwt) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(jwt);
        return Long.valueOf(claims.getBody().getSubject());
    }
}
