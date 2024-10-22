package com.example.projecttrackingapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.projecttrackingapi.AbstractProjectTrackingApiApplicationTest;
import com.example.projecttrackingapi.dto.TeamDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
class TeamControllerTest extends AbstractProjectTrackingApiApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findAllTeams_Returns200() {
        mockMvc.perform(get("/teams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void createNewTeam_Returns200() {
        var newTeam = new TeamDto(null, "Test team");

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(newTeam)))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    private String toJson(final TeamDto team) {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(team);
    }

}