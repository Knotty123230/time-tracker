package com.privat.timetracker.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

/**
 * Data Transfer Object (DTO) for task creation and updates.
 * Contains the required information to create or update a task.
 *
 * @param title       The title of the task. Must not be null, blank, or exceed the length constraints.
 * @param description The description of the task. Must not be blank and should adhere to the length constraints.
 */
public record TaskRequest(
        /**
         * The title of the task.
         *
         * <p>Constraints:</p>
         * <ul>
         *   <li>Cannot be null</li>
         *   <li>Cannot be blank</li>
         *   <li>Length must be between 3 and 500 characters</li>
         * </ul>
         */
        @NotNull(message = "Title cannot be null")
        @Length(message = "Title length must be between 3 and 500 characters", min = 3, max = 500)
        @NotBlank(message = "Title cannot be blank")
        String title,

        /**
         * The description of the task.
         *
         * <p>Constraints:</p>
         * <ul>
         *   <li>Cannot be blank</li>
         *   <li>Length must be between 5 and 10000 characters</li>
         * </ul>
         */
        @Length(message = "Description length must be between 5 and 10000 characters", min = 5, max = 10000)
        @NotBlank(message = "Description cannot be blank")
        String description
) {
}