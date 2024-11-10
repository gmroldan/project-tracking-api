package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewProjectRequest;
import com.example.projecttrackingapi.model.Project;
import com.example.projecttrackingapi.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultProjectServiceTest {

    @InjectMocks
    private DefaultProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Test
    void createNewProject_WhenProjectDoesNotExist_StoresNewProject() {
        var request = new NewProjectRequest("test project");
        var expectedEntity = new Project();
        expectedEntity.setTitle(request.projectTitle());

        projectService.createNewProject(request);

        verify(projectRepository).save(expectedEntity);
    }
}