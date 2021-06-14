package com.ecirstea.user.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public
class JwtProvider {

    @Value("${jwt.token.expiration.time}")
    private String TOKEN_EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String secret;


    public String generateJwtToken(UserDetails user) throws InvalidKeyException {
        Calendar cal = Calendar.getInstance();
        cal.set(2021, Calendar.JULY, 9);
        Date exp = cal.getTime();
        Map<String, Object> map = new HashMap<>();
        map.put("password", user.getPassword());
        return Jwts.builder()
                .setSubject((user.getUsername()))
                .addClaims(map)
                .setIssuedAt(new Date())
                .setExpiration(exp)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String getPasswordFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody().get("password").toString();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(authToken).getBody();
            return true;
        } catch (MalformedJwtException | IllegalArgumentException | UnsupportedJwtException | ExpiredJwtException e) {
            e.printStackTrace();
        }

        return false;
    }
}