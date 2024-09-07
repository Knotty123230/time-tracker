package com.privat.timetracker.controller.dto;

import com.privat.timetracker.entity.TaskStatus;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for task responses.
 * Contains the details of a task, including its status and timestamps.
 *
 * @param id          The unique identifier of the task.
 * @param title       The title of the task.
 * @param description The description of the task.
 * @param status      The current status of the task (e.g., INACTIVE, ACTIVE, COMPLETED).
 * @param startTime   The timestamp when the task was started.
 * @param endTime     The timestamp when the task was ended (may be null if not yet completed).
 * @param createdAt   The timestamp when the task was created.
 * @param editedAt    The timestamp when the task was last edited (may be null if not edited).
 * @param duration    The duration of the task in a human-readable format (e.g., "1h 23m 45s").
 */
public record TaskResponse(
        /**
         * The unique identifier of the task.
         */
        Long id,

        /**
         * The title of the task.
         */
        String title,

        /**
         * The description of the task.
         */
        String description,

        /**
         * The current status of the task.
         *
         * @see TaskStatus
         */
        TaskStatus status,

        /**
         * The timestamp when the task was started.
         */
        LocalDateTime startTime,

        /**
         * The timestamp when the task was ended.
         * May be null if the task is not yet completed.
         */
        LocalDateTime endTime,

        /**
         * The timestamp when the task was created.
         */
        LocalDateTime createdAt,

        /**
         * The timestamp when the task was last edited.
         * May be null if the task has not been edited.
         */
        LocalDateTime editedAt,

        /**
         * The duration of the task in a human-readable format.
         * Example formats: "1h 23m 45s", "2h 10m".
         */
        String duration
) {

}