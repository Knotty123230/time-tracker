package com.privat.timetracker.service;

import com.privat.timetracker.controller.dto.TaskResponse;

public interface TimeTracking {
    TaskResponse startTask(Long taskId);

    TaskResponse stopTask(Long taskId);
}
