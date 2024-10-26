package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.TeamDto;
import com.example.projecttrackingapi.dto.TeamMemberDto;
import com.example.projecttrackingapi.model.Team;
import com.example.projecttrackingapi.model.TeamMember;
import com.example.projecttrackingapi.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
                new Team(1L, "Test Team 1", List.of()),
                new Team(2L, "Test Team 2", List.of())
        );

        var expectedResult = List.of(
                new TeamDto(1L, "Test Team 1", List.of()),
                new TeamDto(2L, "Test Team 2", List.of())
        );

        when(teamRepository.findAll()).thenReturn(teamsStored);

        var result = defaultTeamService.findAllTeams();

        assertEquals(expectedResult, result);
    }

    @Test
    void createNewTeam_SavesNewTeam() {
        var newTeam = new TeamDto(null, "Test 1", List.of());

        defaultTeamService.createNewTeam(newTeam);

        verify(teamRepository).save(any(Team.class));
    }

}