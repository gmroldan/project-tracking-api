package com.example.projecttrackingapi.repository.impl;

import com.example.projecttrackingapi.model.Task;
import com.example.projecttrackingapi.repository.TaskRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryTaskRepository implements TaskRepository {

    private static final Map<Long, Task> TASKS = new ConcurrentHashMap<>();

    static {
        TASKS.put(1L, new Task(1L, "Title #1", "Description #1...", 3, "High", "TODO"));
        TASKS.put(2L, new Task(2L, "Title #2", "Description #2...", 13, "Low", "TODO"));
        TASKS.put(3L, new Task(3L, "Title #3", "Description #3...", 5, "Medium", "IN_PROGRESS"));
        TASKS.put(4L, new Task(4L, "Title #4", "Description #4...", 8, "High", "TODO"));
        TASKS.put(5L, new Task(5L, "Title #5", "Description #5...", 5, "Low", "DONE"));
    }

    @Override
    public Task save(Task task) {
        if (task.getId() == null
                || !TASKS.containsKey(task.getId())) {
            task.setId((long) (TASKS.size() + 1));
        }

        TASKS.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(TASKS.get(id));
    }

    @Override
    public List<Task> findAll(int page, int size) {
        var startIndex = (page - 1) * size;
        var endIndex = Math.min(startIndex + size, TASKS.size());

        if (startIndex >= TASKS.size() || startIndex < 0) {
            return List.of();
        }

        var entries = new ArrayList<>(TASKS.entrySet());
        var sublist = entries.subList(startIndex, endIndex);

        return sublist.stream()
                .map(Map.Entry::getValue)
                .toList();
    }
}
