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
import java.util.function.Function;
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

        storedTeam.setName(request.name());
        syncTeamMembers(storedTeam, request.members());

        teamRepository.save(storedTeam);
    }

    private void syncTeamMembers(Team storedTeam, List<TeamMemberDto> members) {
        var newTeamMembers = members.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toMap(TeamMember::getUserId, Function.identity()));

        var currentTeamMembersMap = storedTeam.getTeamMembers()
                .stream()
                .collect(Collectors.toMap(TeamMember::getUserId, Function.identity()));

        storedTeam.getTeamMembers().removeIf(storedTeamMember -> !newTeamMembers.containsKey(storedTeamMember.getUserId()));

        newTeamMembers.forEach((key, value) -> {
            var existingTeamMember = currentTeamMembersMap.get(key);
            if (existingTeamMember == null) {
                storedTeam.getTeamMembers().add(value);
            } else {
                existingTeamMember.setRole(value.getRole());
            }
        });
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
