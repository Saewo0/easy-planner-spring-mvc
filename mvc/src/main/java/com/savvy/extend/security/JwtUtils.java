package com.savvy.extend.security;

import com.savvy.extend.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtils {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_AUDIENCE = "audience";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.expiration}")
    private int expiration = 86400;

    @Value("${jwt.secret}")
    private String secret;

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                   .setClaims(claims)
                   .setExpiration(generateExpirationDate())
                   .signWith(SignatureAlgorithm.HS512, secret)
                   .compact();
    }

    public String getUsernameFromToken(String token) throws InvalidTokenException {
        final Claims claims = getClaimsFromToken(token);
        String username = claims.getSubject();
        return username;
    }

    private Claims getClaimsFromToken(String token) throws InvalidTokenException {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid Token", e);
        }
        return claims;
    }
}
