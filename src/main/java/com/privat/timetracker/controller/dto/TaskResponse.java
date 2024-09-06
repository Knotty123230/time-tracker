package com.privat.timetracker.controller.dto;

import com.privat.timetracker.entity.Task;
import com.privat.timetracker.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        LocalDateTime startTime,
        LocalDateTime endTime
) {

}
