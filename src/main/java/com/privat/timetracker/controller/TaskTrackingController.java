package com.privat.timetracker.controller;

import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.service.TimeTracking;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing task tracking operations.
 * This controller provides endpoints to start and stop tasks in the time tracking system.
 */
@RestController
@RequestMapping(value = "/api/v1/tasks")
@RequiredArgsConstructor
public class TaskTrackingController implements ITaskTrackingController {

    private final TimeTracking timeTracking;

    /**
     * Starts a task with the given ID.
     *
     * @param id the ID of the task to start
     * @return {@link TaskResponse} containing the updated task details after starting
     */
    @Override
    @PostMapping("/{id}/start")
    public TaskResponse startTask(@PathVariable Long id) {
        return timeTracking.startTask(id);
    }

    /**
     * Stops a task with the given ID.
     *
     * @param id the ID of the task to stop
     * @return {@link TaskResponse} containing the updated task details after stopping
     */
    @Override
    @PostMapping("/{id}/stop")
    public TaskResponse stopTask(@PathVariable Long id) {
        return timeTracking.stopTask(id);
    }
}