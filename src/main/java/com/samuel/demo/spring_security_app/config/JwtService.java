package com.samuel.demo.spring_security_app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Configuration
public class JwtService {


    private final String SECRETE_KEY = "ZHESmOUfuVGWmtnHC9Ygx0dp0v7R4b2xKD8Vczsnpc1VRd+AWjj+G5f/kI3JoQ2BvLXNrl516J4BjZTB1p2s1g==";

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getPayload();
    }

    private Key getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(SECRETE_KEY);

        return Keys.hmacShaKeyFor(keyBytes);

    }

    // extracts single claims
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){

        final Claims claim = extractAllClaims(token);

        return claimsResolver.apply(claim);

        /*
        claims contains user details
         */
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // Generate token for private use only
    private String generateToken(Map<String, Object> extractClaim, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extractClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // for public use alo
    public String generatedToken(UserDetails userDetails){
        return (generateToken(new HashMap<>(), userDetails));
    }

    // check if the token is valid or not
    public Boolean isTokenValid(String token, UserDetails userDetails){
         final String userName = extractUsername(token);

         return(userName.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }


    // return toke expired true or false
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // return expiration date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
