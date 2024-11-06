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
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Optional<TeamDto> findById(final Long id) {
        return teamRepository.findById(id).map(this::mapToDto);
    }

    @Override
    public void updateTeam(final TeamDto request) {
        final var storedTeam = teamRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Team does not exist"));

        final var newTeamMembers = request.members()
                .stream()
                .collect(Collectors.toMap(TeamMemberDto::id, TeamMemberDto::role));

        storedTeam.setName(request.name());
        storedTeam.getTeamMembers().removeIf(teamMember -> !newTeamMembers.containsKey(teamMember.getUserId()));
        storedTeam.getTeamMembers().forEach(teamMember -> teamMember.setRole(newTeamMembers.getOrDefault(teamMember.getUserId(), "DEFAULT_ROLE")));

        teamRepository.save(storedTeam);
    }


    private TeamDto mapToDto(final Team team) {
        return new TeamDto(team.getId(), team.getName(), mapToDto(team.getTeamMembers()));
    }

    private List<TeamMemberDto> mapToDto(final List<TeamMember> teamMembers) {
        return teamMembers.stream().map(this::mapToDto).toList();
    }

    private TeamMemberDto mapToDto(final TeamMember teamMember) {
        return new TeamMemberDto(teamMember.getUserId(), teamMember.getRole());
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
