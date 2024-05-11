package com.example.projecttrackingapi.controller;

import com.example.projecttrackingapi.AbstractProjectTrackingApiApplicationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class UserControllerTest extends AbstractProjectTrackingApiApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAll_Returns200() {
        mockMvc.perform(get("/users?page=0&size=2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}