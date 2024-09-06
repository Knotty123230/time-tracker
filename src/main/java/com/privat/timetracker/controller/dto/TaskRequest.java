package com.privat.timetracker.controller.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record TaskRequest(
        @NotNull @Length(min = 3, max = 500) String title,
        @Length(min = 5, max = 10000) String description
) {
}
