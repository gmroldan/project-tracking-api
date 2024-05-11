package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewTaskRequest;
import com.example.projecttrackingapi.dto.TaskDto;
import com.example.projecttrackingapi.exception.TaskNotFoundException;
import com.example.projecttrackingapi.model.Task;
import com.example.projecttrackingapi.repository.TaskRepository;
import com.example.projecttrackingapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DefaultTaskService implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public void createNewTask(NewTaskRequest request) {
        var model = new Task();
        model.setTitle(request.title());
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
        var pageRequest = PageRequest.of(page, size);
        return StreamSupport.stream(taskRepository.findAll(pageRequest).spliterator(), false)
                .map(this::toDto)
                .toList();
    }

    private TaskDto toDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getStoryPoints(), task.getPriority(), task.getStatus(), task.getUserAssigned());
    }

    @Override
    public void update(TaskDto taskDto) {
        taskRepository.findById(taskDto.id())
                .ifPresentOrElse(entity -> {
                    updateEntity(entity, taskDto);
                    taskRepository.save(entity);
                }, () -> {
                    throw new TaskNotFoundException(taskDto.id());
                });
    }

    private void updateEntity(Task entity, TaskDto dto) {
        entity.setTitle(dto.title());
        entity.setDescription(dto.description());
        entity.setStoryPoints(dto.storyPoints());
        entity.setPriority(dto.priority());
        entity.setStatus(dto.status());
    }
}
