package com.example.projecttrackingapi.security;

import com.example.projecttrackingapi.dto.UserDto;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

class JwtUtilTest {

    private final JwtUtil jwtUtil = new JwtUtil("MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDE=", 3600000L);

    @Test
    void tokenGenerationAndValidation() {
        final var userDto = new UserDto(1L, "hsimpson", "Homer", "Simpson");

        final var token = jwtUtil.createToken(userDto);

        assertNotNull(token);
        assertTrue(jwtUtil.isTokenValid(token));
    }

}