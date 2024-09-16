package com.example.projecttrackingapi.security;

import com.example.projecttrackingapi.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        this.jwtUtil = new JwtUtil("MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDE=", 3600000L);
    }

    @Test
    void createToken_whenUserHasJustLoggedIn_ReturnsNewToken() {
        final var userDto = new UserDto(1L, "hsimpson", "Homer", "Simpson");

        final var token = jwtUtil.createToken(userDto);

        assertNotNull(token);
        assertTrue(jwtUtil.isTokenValid(token));
    }

    @Test
    void invalidateToken_whenUserHasJustLoggedIn_addTokenToBlackList() {
        final var userDto = new UserDto(1L, "hsimpson", "Homer", "Simpson");

        final var token = jwtUtil.createToken(userDto);

        assertNotNull(token);
        assertTrue(jwtUtil.isTokenValid(token));

        jwtUtil.invalidateToken(token);

        assertFalse(jwtUtil.isTokenValid(token));
    }

}