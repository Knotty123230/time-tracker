package com.privat.timetracker.controller;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tasks in the time tracking system.
 * Provides endpoints for creating, editing, retrieving, and deleting tasks.
 */
@RestController
@RequestMapping(value = "api/v1/tasks")
@RequiredArgsConstructor
public class TaskController implements ITaskController {

    private final TaskService taskService;

    /**
     * Creates a new task.
     *
     * @param taskRequest the request body containing task details
     * @return {@link TaskResponse} containing the details of the newly created task
     */
    @PostMapping
    @Override
    public TaskResponse createTask(@RequestBody @Valid TaskRequest taskRequest) {
        return taskService.createTask(taskRequest);
    }

    /**
     * Edits an existing task by its ID.
     *
     * @param id          the ID of the task to edit
     * @param taskRequest the request body containing updated task details
     * @return {@link TaskResponse} containing the updated task details
     */
    @PutMapping("/{id}")
    @Override
    public TaskResponse editTask(@PathVariable Long id, @RequestBody @Valid TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }

    /**
     * Retrieves all tasks.
     *
     * @return a list of {@link TaskResponse} objects containing details of all tasks
     */
    @GetMapping
    @Override
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return {@link TaskResponse} containing the details of the requested task
     */
    @GetMapping("/{id}")
    @Override
    public TaskResponse getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param id the ID of the task to delete
     * @return {@link ResponseEntity} with HTTP status OK if the deletion was successful
     */
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}