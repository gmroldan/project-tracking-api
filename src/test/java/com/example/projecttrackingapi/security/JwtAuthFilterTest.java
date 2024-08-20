package com.example.projecttrackingapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    private static final String VALID_TOKEN = "valid_token";
    private static final String AUTH_HEADER_WITH_VALID_TOKEN = "Bearer " + VALID_TOKEN;
    private static final String INVALID_TOKEN = "valid_token";
    private static final String AUTH_HEADER_WITH_INVALID_TOKEN = "Bearer " + INVALID_TOKEN;
    private static final String USERNAME = "test1";

    @InjectMocks
    private JwtAuthFilter authFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    private MockHttpServletRequest request;

    private HttpServletResponse response;

    @BeforeEach
    void setup() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.clearContext();
    }

    @Test
    @SneakyThrows
    void doFilterInternal_WhenAuthorizationTokenHasValidToken_UpdatesSecurityContext() {
        request.addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_WITH_VALID_TOKEN);
        final var user = new User(USERNAME, "", new ArrayList<>());

        when(jwtUtil.isTokenValid(VALID_TOKEN)).thenReturn(true);
        when(jwtUtil.extractUsername(VALID_TOKEN)).thenReturn(USERNAME);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(user);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @SneakyThrows
    void doFilterInternal_WhenAuthorizationTokenIsNotPresent_SecurityContextIsNotAuthenticated() {
        authFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtUtil, never()).isTokenValid(anyString());
        verify(jwtUtil, never()).extractUsername(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @SneakyThrows
    void doFilterInternal_WhenAuthorizationTokenHasInvalidToken_SecurityContextIsNotAuthenticated() {
        request.addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_WITH_INVALID_TOKEN);

        when(jwtUtil.isTokenValid(INVALID_TOKEN)).thenReturn(false);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtUtil).isTokenValid(anyString());
        verify(jwtUtil, never()).extractUsername(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}