package com.openlab.utilisateurs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Component
public class JwtUtil {
    private final Key secretKey = Keys.hmacShaKeyFor("05f26aac28bc1d2b2ef4306e1195bd5320bf7e46ad2d988ec556b61beb78be65".getBytes());


    // Methode pour extraire l'username du token JWT
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    //Methode pour extraire la date d'expiration du token JWT
    public Date extractExperation(String token){
        return extractClaim(token, Claims::getExpiration);
    }


    //Methode génerique pour extraire n'importe qu'elle informations du token JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolve){
        final Claims claims =  extractAllClaims(token); // Extraction des claims (données) du token JWT
        return claimsResolve.apply(claims); // applique la fonction passée en argument
    }


    // Extraction des claims
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Verifier si le token est expiré
    private Boolean isTokenExpired(String token){
        return extractExperation(token).before(new Date());
    }


    //Creation du token JWT avec la signature
    private String createToken(String subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // expiration dans 10h
                .signWith(secretKey, SignatureAlgorithm.HS256) // Utilisation de la clé secrète et de l'algorithme HS256
                .compact();
    }

    //Génération d'un token JWT avec un username
    public String generateToken(String username){
        return createToken(username);
    }

  // Validation du token JWT
    public Boolean validateToken(String token, String username){
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }


}
