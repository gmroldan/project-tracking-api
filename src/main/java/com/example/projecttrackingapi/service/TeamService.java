package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.NewTeamRequest;
import com.example.projecttrackingapi.dto.TeamDto;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    List<TeamDto> findAllTeams();

    void createNewTeam(NewTeamRequest request);

    Optional<TeamDto> findById(Long id);

    void updateTeam(TeamDto request);
}
