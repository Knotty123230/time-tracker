package com.privat.timetracker.controller;

import com.privat.timetracker.controller.dto.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "API відстеження задач", description = "API для відстеження часу задач")
public interface ITaskTrackingController {

    @Operation(summary = "Почати відстеження часу для задачі", description = "Починає відстеження часу для вказаного ID задачі")
    @PostMapping("/{id}/start")
    TaskResponse startTask(@PathVariable Long id);

    @Operation(summary = "Зупинити відстеження часу для задачі", description = "Зупиняє відстеження часу для вказаного ID задачі")
    @PostMapping("/{id}/stop")
    TaskResponse stopTask(@PathVariable Long id);
}