package com.example.projecttrackingapi.service;

import com.example.projecttrackingapi.dto.NewProjectRequest;

public interface ProjectService {

    void createNewProject(NewProjectRequest request);
}
