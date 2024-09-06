package com.privat.timetracker.controller;

import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.service.TimeTracking;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tasks")
@RequiredArgsConstructor
public class TaskTrackingController {
    private final TimeTracking timeTracking;

    @PostMapping("/{id}/start")
    public TaskResponse startTask(@PathVariable Long id) {
        return timeTracking.startTask(id);
    }

    @PostMapping("/{id}/stop")
    public TaskResponse stopTask(@PathVariable Long id) {
        return timeTracking.stopTask(id);
    }
}
