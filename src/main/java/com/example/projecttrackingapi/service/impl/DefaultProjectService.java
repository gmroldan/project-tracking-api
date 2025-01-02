package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewProjectRequest;
import com.example.projecttrackingapi.dto.ProjectDto;
import com.example.projecttrackingapi.model.Project;
import com.example.projecttrackingapi.repository.ProjectRepository;
import com.example.projecttrackingapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class DefaultProjectService implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public void createNewProject(final NewProjectRequest request) {
        var entity = mapToEntity(request);

        projectRepository.save(entity);
    }

    @Override
    public List<ProjectDto> findAll() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public Optional<ProjectDto> findById(Long id) {
        return projectRepository.findById(id).map(this::mapToDto);
    }

    @Override
    public void updateProject(final ProjectDto request) {
        final var storedProject = projectRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("Project does not exist"));

        storedProject.setTitle(request.title());

        projectRepository.save(storedProject);
    }

    private ProjectDto mapToDto(Project project) {
        return new ProjectDto(project.getId(), project.getTitle());
    }

    private Project mapToEntity(final NewProjectRequest request) {
        return new Project(null, request.title());
    }
}
