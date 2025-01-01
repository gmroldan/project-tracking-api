package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewProjectRequest;
import com.example.projecttrackingapi.dto.ProjectDto;
import com.example.projecttrackingapi.model.Project;
import com.example.projecttrackingapi.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        expectedEntity.setTitle(request.title());

        projectService.createNewProject(request);

        verify(projectRepository).save(expectedEntity);
    }

    @Test
    void findAll_WhenProjectsExist_ReturnsList() {
        var storedProjects = List.of(
                new Project(1L, "First Project"),
                new Project(2L, "Second Project")
        );

        var expectedResult = List.of(
                new ProjectDto(1L, "First Project"),
                new ProjectDto(2L, "Second Project")
        );

        when(projectRepository.findAll()).thenReturn(storedProjects);

        var result = projectService.findAll();

        assertEquals(expectedResult, result);
    }

    @Test
    void findAll_WhenThereAreNoProjects_ReturnsEmptyList() {
        when(projectRepository.findAll()).thenReturn(List.of());

        var result = projectService.findAll();

        assertTrue(result.isEmpty());
    }
}