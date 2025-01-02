package com.example.projecttrackingapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.projecttrackingapi.AbstractProjectTrackingApiApplicationTest;
import com.example.projecttrackingapi.dto.NewProjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
class ProjectControllerTest extends AbstractProjectTrackingApiApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findProjectUsers_Returns200() {
        mockMvc.perform(get("/projects/1/team")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void findSprintTasks_Returns200() {
        mockMvc.perform(get("/projects/1/board")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void findAllProjects_Returns200() {
        mockMvc.perform(get("/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void createNewProject_Returns200() {
        var newProject = new NewProjectRequest("test project 1");

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(newProject)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void findProjectById_WhenProjectExists_Returns200() {
        mockMvc.perform(get("/projects/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void findProjectById_WhenProjectDoesNotExist_Returns404() {
        mockMvc.perform(get("/projects/10000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    private String toJson(final NewProjectRequest request) {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(request);
    }
}