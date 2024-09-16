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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class JwtUtil {

    private static final Set<String> TOKEN_BLACK_LIST = new HashSet<>();

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

        return !tokenExpiration.before(new Date()) && !isBlackListed(token);
    }

    public String extractUsername(final String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(final String token) {
        final var jwtParser = Jwts.parser().verifyWith(getSignInKey()).build();
        return jwtParser.parseSignedClaims(token).getPayload();
    }

    private boolean isBlackListed(final String token) {
        return TOKEN_BLACK_LIST.contains(token);
    }

    public void invalidateToken(final String token) {
        TOKEN_BLACK_LIST.add(token);
    }
}
