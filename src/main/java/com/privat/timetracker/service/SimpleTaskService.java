package com.privat.timetracker.service;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.entity.Task;
import com.privat.timetracker.entity.TaskStatus;
import com.privat.timetracker.exception.constants.ErrorMessages;
import com.privat.timetracker.exception.exceptions.TaskNotFoundException;
import com.privat.timetracker.mapping.TaskMapper;
import com.privat.timetracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing tasks.
 * <p>
 * This service provides operations for creating, updating, retrieving, and deleting tasks. It uses
 * the {@link TaskMapper} to convert between DTOs and entities and interacts with the {@link TaskRepository}
 * to perform CRUD operations.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    /**
     * Creates a new task.
     * <p>
     * This method maps the {@link TaskRequest} to a {@link Task} entity, sets the status to {@link TaskStatus#CREATED},
     * and sets the creation time to the current time. The task is then saved to the repository.
     * </p>
     *
     * @param taskRequest the request containing task details
     * @return the created task as a {@link TaskResponse} DTO
     */
    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = taskMapper.toEntity(taskRequest);
        task.setStatus(TaskStatus.CREATED);
        task.setCreatedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    /**
     * Updates an existing task.
     * <p>
     * This method finds a task by its ID, updates it with the values from {@link TaskRequest}, and sets the update time
     * to the current time. The updated task is then saved to the repository.
     * </p>
     *
     * @param taskId      the ID of the task to update
     * @param taskRequest the request containing updated task details
     * @return the updated task as a {@link TaskResponse} DTO
     * @throws TaskNotFoundException if no task is found with the given ID
     */
    @Override
    @Transactional
    public TaskResponse updateTask(Long taskId, TaskRequest taskRequest) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND.formatted(taskId)));
        Task updatedTaskFromDto = taskMapper.updateTaskFromRequest(taskRequest, task);
        updatedTaskFromDto.setUpdatedAt(LocalDateTime.now());
        Task updatedTask = taskRepository.save(updatedTaskFromDto);
        return taskMapper.toDto(updatedTask);
    }

    /**
     * Retrieves a task by its ID.
     * <p>
     * This method finds a task by its ID and returns it as a {@link TaskResponse} DTO.
     * </p>
     *
     * @param taskId the ID of the task to retrieve
     * @return the task as a {@link TaskResponse} DTO
     * @throws TaskNotFoundException if no task is found with the given ID
     */
    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND.formatted(taskId)));
        return taskMapper.toDto(task);
    }

    /**
     * Retrieves all tasks.
     * <p>
     * This method fetches all tasks from the repository and returns them as a list of {@link TaskResponse} DTOs.
     * </p>
     *
     * @return a list of all tasks as {@link TaskResponse} DTOs
     */
    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a task by its ID.
     * <p>
     * This method deletes a task with the given ID from the repository. If no task is found with the ID, a
     * {@link TaskNotFoundException} is thrown.
     * </p>
     *
     * @param taskId the ID of the task to delete
     * @throws TaskNotFoundException if no task is found with the given ID
     */
    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND.formatted(taskId));
        }
        taskRepository.deleteById(taskId);
    }
}