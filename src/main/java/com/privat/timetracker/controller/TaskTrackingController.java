package com.privat.timetracker.controller;

import com.privat.timetracker.controller.dto.TaskResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tasks")
public class TaskTrackingController {
    @PostMapping("/{id}/start")
    public TaskResponse startTask(@PathVariable String id) {
        return null;
    }

    @PostMapping("/{id}/stop")
    public TaskResponse stopTask(@PathVariable String id) {
        return null;
    }
}
