package istemail.istemail.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import istemail.istemail.config.AppConfiguration;
import istemail.istemail.models.User;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Autowired
    private AppConfiguration appConfiguration;

    private final SecretKey jwtSecret;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().name());
        claims.put("isVerified", user.isVerified());
        
        return createToken(claims, user.getEmail());
    }

    @SuppressWarnings("deprecation")
    private String createToken(Map<String, Object> claims, String subject) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getEncoded());
        
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + appConfiguration.getJwtExpiration()))
            .signWith(secretKey)
            .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUserId(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("userId", String.class);
    }

    public String extractUserRole(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("role", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtSecret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Object> getTokenClaims(String token) {
        Claims claims = extractAllClaims(token);
        Map<String, Object> claimsMap = new HashMap<>();
        
        claimsMap.put("userId", claims.get("userId", String.class));
        claimsMap.put("email", claims.get("email", String.class));
        claimsMap.put("firstName", claims.get("firstName", String.class));
        claimsMap.put("lastName", claims.get("lastName", String.class));
        claimsMap.put("role", claims.get("role", String.class));
        claimsMap.put("isVerified", claims.get("isVerified", Boolean.class));
        claimsMap.put("title", claims.get("title", String.class));
        claimsMap.put("phoneNumber", claims.get("phoneNumber", String.class));
        
        return claimsMap;
    }
}