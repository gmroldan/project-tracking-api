package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.TeamDto;
import com.example.projecttrackingapi.model.Team;
import com.example.projecttrackingapi.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultTeamServiceTest {

    @InjectMocks
    private DefaultTeamService defaultTeamService;

    @Mock
    private TeamRepository teamRepository;

    @Test
    void findAllTeams_WhenTeamsExistInDB_ReturnsListOfTeams() {
        var teamsStored = List.of(
                new Team(1L, "Test Team 1"),
                new Team(2L, "Test Team 2")
        );

        var expectedResult = List.of(
                new TeamDto(1L, "Test Team 1"),
                new TeamDto(2L, "Test Team 2")
        );

        when(teamRepository.findAll()).thenReturn(teamsStored);

        var result = defaultTeamService.findAllTeams();

        assertEquals(expectedResult, result);
    }

}