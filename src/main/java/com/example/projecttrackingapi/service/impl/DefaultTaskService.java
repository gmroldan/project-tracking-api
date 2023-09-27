package com.example.projecttrackingapi.service.impl;

import com.example.projecttrackingapi.dto.NewTaskRequest;
import com.example.projecttrackingapi.model.Task;
import com.example.projecttrackingapi.repository.TaskRepository;
import com.example.projecttrackingapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
