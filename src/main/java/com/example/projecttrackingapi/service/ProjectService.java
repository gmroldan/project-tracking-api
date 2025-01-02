package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.NewProjectRequest;
import com.example.projecttrackingapi.dto.ProjectDto;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    void createNewProject(NewProjectRequest request);

    List<ProjectDto> findAll();

    Optional<ProjectDto> findById(Long id);

    void updateProject(ProjectDto request);
}
