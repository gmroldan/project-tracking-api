package com.example.projecttrackingapi.security;

import com.example.projecttrackingapi.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class JwtUtil {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value(("${security.jwt.expiration-time:36_000}"))
    private Long jwtExpirationTime;

    public String createToken(final UserDto userAuthenticated) {
        return Jwts.builder()
                .claims(Map.of())
                .subject(userAuthenticated.username())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private SecretKey getSignInKey() {
        final var keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(final String token) {
        final var claims = extractClaims(token);
        final var tokenExpiration = claims.getExpiration();

        return !tokenExpiration.before(new Date());
    }

    public String extractUsername(final String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(final String token) {
        final var jwtParser = Jwts.parser().verifyWith(getSignInKey()).build();
        final var claims = jwtParser.parseSignedClaims(token).getPayload();
        return claims;
    }
}
