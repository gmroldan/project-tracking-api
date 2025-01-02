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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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

    @Test
    void findById_WhenProjectExists_ReturnsOptionalWithProject() {
        var expectedProject = new ProjectDto(3L, "Test Project");

        when(projectRepository.findById(3L)).thenReturn(Optional.of(new Project(3L, "Test Project")));

        var result = projectService.findById(3L);

        assertTrue(result.isPresent());
        assertEquals(expectedProject, result.get());
    }

    @Test
    void findById_WhenProjectDoesNotExist_ReturnsEmptyOptional() {
        when(projectRepository.findById(3L)).thenReturn(Optional.empty());

        var result = projectService.findById(3L);

        assertTrue(result.isEmpty());
    }

    @Test
    void updateProject_WhenProjectExists_UpdatesExistingRecord() {
        var request = new ProjectDto(11L, "Modified");
        var storedProject = new Project(11L, "Original Title");
        var expectedUpdatedEntity = new Project(11L, "Modified");

        when(projectRepository.findById(11L)).thenReturn(Optional.of(storedProject));

        projectService.updateProject(request);

        verify(projectRepository).save(expectedUpdatedEntity);
    }

    @Test
    void updateProject_WhenProjectDoesNotExist_ThrowsException() {
        var request = new ProjectDto(11L, "Modified");

        when(projectRepository.findById(request.id())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projectService.updateProject(request));

        verify(projectRepository, never()).save(any());
    }
}