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
class SprintControllerTest extends AbstractProjectTrackingApiApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findSprintTasks_Returns200() {
        mockMvc.perform(get("/sprints/1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}