package com.privat.timetracker.controller;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ITaskController {

    @Operation(summary = "Створити нове завдання", description = "Цей метод створює нове завдання з вказаними заголовком і описом")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Завдання успішно створене",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Невалідні дані",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Внутрішня помилка сервера",
                    content = @Content)
    })
    TaskResponse createTask(@RequestBody @Valid TaskRequest taskRequest);

    @Operation(summary = "Оновити завдання", description = "Цей метод оновлює існуюче завдання за ідентифікатором")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Завдання успішно оновлене",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Завдання не знайдено",
                    content = @Content)
    })
    TaskResponse editTask(@Parameter(description = "Ідентифікатор завдання") @PathVariable Long id,
                          @RequestBody @Valid TaskRequest taskRequest);

    @Operation(summary = "Отримати всі завдання", description = "Цей метод повертає список всіх завдань")
    @ApiResponse(responseCode = "200", description = "Успішне отримання списку завдань",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskResponse.class))})
    List<TaskResponse> getAllTasks();

    @Operation(summary = "Отримати завдання за ідентифікатором", description = "Цей метод повертає завдання за його ідентифікатором")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успішне отримання завдання",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Завдання не знайдено",
                    content = @Content)
    })
    TaskResponse getTask(@Parameter(description = "Ідентифікатор завдання") @PathVariable Long id);

    @Operation(summary = "Видалити завдання", description = "Цей метод видаляє завдання за його ідентифікатором")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Завдання успішно видалено",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Завдання не знайдено",
                    content = @Content)
    })
    ResponseEntity<?> deleteTask(@Parameter(description = "Ідентифікатор завдання") @PathVariable Long id);
}