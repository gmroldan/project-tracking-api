package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.NewTeamRequest;
import com.example.projecttrackingapi.dto.TeamDto;

import java.util.List;

public interface TeamService {

    List<TeamDto> findAllTeams();

    void createNewTeam(NewTeamRequest request);
}
