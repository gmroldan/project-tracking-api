package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewTeamRequest;
import com.example.projecttrackingapi.dto.TeamDto;
import com.example.projecttrackingapi.dto.TeamMemberDto;
import com.example.projecttrackingapi.model.Team;
import com.example.projecttrackingapi.model.TeamMember;
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

    @Override
    public void createNewTeam(final NewTeamRequest request) {
        var team = mapToEntity(request);

        teamRepository.save(team);
    }

    private TeamDto mapToDto(final Team team) {
        return new TeamDto(team.getId(), team.getName(), List.of());
    }

    private Team mapToEntity(final NewTeamRequest teamDto) {
        var teamMembers = mapToEntity(teamDto.members());
        var team = new Team();
        team.setName(teamDto.name());
        team.setTeamMembers(teamMembers);

        return team;
    }

    private List<TeamMember> mapToEntity(final List<TeamMemberDto> membersDto) {
        return membersDto.stream()
                .map(this::mapToEntity)
                .toList();
    }

    private TeamMember mapToEntity(final TeamMemberDto dto) {
        return new TeamMember(dto.id(), dto.role());
    }
}
