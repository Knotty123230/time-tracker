package com.privat.timetracker.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record TaskRequest(
        @NotNull(message = "Заголовок не може бути null")
        @Length(message = "Довжина заголовку має бути більше 3 і менше 500", min = 3, max = 500)
        @NotBlank(message = "Заголовок не може бути пустим")
        String title,
        @Length(message = "Довжина опису має бути більше 5 і менше 10000", min = 5, max = 10000)
        @NotBlank(message = "Опис не може бути пустим")
        String description
) {
}
