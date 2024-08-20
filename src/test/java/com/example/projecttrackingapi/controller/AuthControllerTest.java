package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.AbstractProjectTrackingApiApplicationTest;
import com.example.projecttrackingapi.dto.CredentialsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class AuthControllerTest extends AbstractProjectTrackingApiApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Test
    void login_WhenUserAndPassExist_Returns200() {
        final var credentials = new CredentialsDto("admin", "123456");

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(credentials)))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void login_WhenUserAndPassDoNotExist_Returns401() {
        final var credentials = new CredentialsDto("admin", "123456");

        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(credentials)))
                .andExpect(status().isUnauthorized());
    }

    @SneakyThrows
    private String toJson(Object object) {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}