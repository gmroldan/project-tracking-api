package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.NewProjectRequest;
import com.example.projecttrackingapi.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    void createNewProject(NewProjectRequest request);

    List<ProjectDto> findAll();
}
