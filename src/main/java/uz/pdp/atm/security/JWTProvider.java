package uz.pdp.atm.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTProvider {
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, 3600_000L);
    }

    public boolean tokenIsValid(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = getAllClaims(token);
        if (claims != null) {
            return claimsTFunction.apply(claims);
        }
        return null;
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, Long duration) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + duration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getKey() {
        byte[] bytes = Decoders.BASE64.decode("404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        return Keys.hmacShaKeyFor(bytes);
    }
    private Claims getAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
