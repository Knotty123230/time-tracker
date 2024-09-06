package com.privat.timetracker.controller;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskResponse createTask(@RequestBody TaskRequest taskRequest) {
        return taskService.createTask(taskRequest);
    }

    @PutMapping("/{id}")
    public TaskResponse editTask(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }

    @GetMapping
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }
}
