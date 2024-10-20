package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.TeamDto;
import com.example.projecttrackingapi.model.Team;
import com.example.projecttrackingapi.repository.TeamRepository;
import com.example.projecttrackingapi.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTeamService implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public List<TeamDto> findAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private TeamDto mapToDto(final Team team) {
        return new TeamDto(team.getId(), team.getName());
    }
}
