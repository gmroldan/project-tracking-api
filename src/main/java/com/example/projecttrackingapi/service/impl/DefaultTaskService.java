package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewTaskRequest;
import com.example.projecttrackingapi.dto.TaskDto;
import com.example.projecttrackingapi.model.Task;
import com.example.projecttrackingapi.repository.TaskRepository;
import com.example.projecttrackingapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public void createNewTask(NewTaskRequest request) {
        var model = new Task();
        model.setTitle(request.tittle());
        model.setDescription(request.description());
        model.setStoryPoints(request.storyPoints());
        model.setPriority(request.priority());
        model.setStatus(request.status());

        taskRepository.save(model);
    }

    @Override
    public Optional<TaskDto> findById(Long id) {
        return taskRepository.findById(id)
                .map(this::toDto)
                .map(Optional::of)
                .orElse(Optional.empty());
    }

    @Override
    public List<TaskDto> findAll(int page, int size) {
        return taskRepository.findAll(page, size)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private TaskDto toDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getStoryPoints(), task.getPriority(), task.getStatus());
    }
}
