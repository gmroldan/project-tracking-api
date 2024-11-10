package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewProjectRequest;
import com.example.projecttrackingapi.dto.ProjectDto;
import com.example.projecttrackingapi.model.Project;
import com.example.projecttrackingapi.repository.ProjectRepository;
import com.example.projecttrackingapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private ProjectDto mapToDto(Project project) {
        return new ProjectDto(project.getId(), project.getTitle());
    }

    private Project mapToEntity(final NewProjectRequest request) {
        return new Project(null, request.projectTitle());
    }
}
