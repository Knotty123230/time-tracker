package com.privat.timetracker.service;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskRequest task);

    TaskResponse getTask(Long taskId);

    void deleteTask(Long taskId);

    TaskResponse updateTask(Long taskId, TaskRequest taskRequest);

    List<TaskResponse> getAllTasks();
}
