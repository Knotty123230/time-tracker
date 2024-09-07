package com.privat.timetracker.service;

import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.entity.Task;
import com.privat.timetracker.entity.TaskStatus;
import com.privat.timetracker.exception.constants.ErrorMessages;
import com.privat.timetracker.exception.exceptions.*;
import com.privat.timetracker.mapping.TaskMapper;
import com.privat.timetracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service class for managing task time tracking operations.
 * <p>
 * This service provides operations for starting and stopping tasks. It ensures that tasks are properly updated
 * with their start and end times and handles errors that may occur during these operations.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TaskTimeTrackingService implements TimeTracking {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    /**
     * Starts a task with the given ID.
     * <p>
     * This method sets the status of the task to {@link TaskStatus#ACTIVE} and sets the start time to the current
     * time. If the task is already active, a {@link TaskAlreadyStarted} exception is thrown. If an error occurs,
     * a {@link TaskTimeException} is thrown.
     * </p>
     *
     * @param taskId the ID of the task to start
     * @return the updated task as a {@link TaskResponse} DTO
     * @throws TaskNotFoundException if no task is found with the given ID
     * @throws TaskAlreadyStarted    if the task is already started
     * @throws TaskTimeException     if an error occurs while starting the task
     */
    @Override
    public TaskResponse startTask(Long taskId) {
        Task task = getTask(taskId);
        if (task.getStatus().equals(TaskStatus.ACTIVE))
            throw new TaskAlreadyStarted(ErrorMessages.TASK_TIME_EXCEPTION.formatted("start", taskId, "Task already started"));
        try {
            task.setStatus(TaskStatus.ACTIVE);
            task.setStartTime(LocalDateTime.now());
            Task savedTask = taskRepository.save(task);
            return taskMapper.toDto(savedTask);
        } catch (Exception ex) {
            throw new TaskTimeException(ErrorMessages.TASK_TIME_EXCEPTION.formatted("start", taskId, ex.getMessage()));
        }
    }

    /**
     * Stops a task with the given ID.
     * <p>
     * This method sets the status of the task to {@link TaskStatus#INACTIVE} and sets the end time to the current
     * time. If the task is not started or is already stopped, a {@link TaskNotStartedException} or
     * {@link TaskAlreadyStopped} exception is thrown, respectively. If an error occurs, a {@link TaskTimeException}
     * is thrown.
     * </p>
     *
     * @param taskId the ID of the task to stop
     * @return the updated task as a {@link TaskResponse} DTO
     * @throws TaskNotFoundException   if no task is found with the given ID
     * @throws TaskNotStartedException if the task is not started
     * @throws TaskAlreadyStopped      if the task is already stopped
     * @throws TaskTimeException       if an error occurs while stopping the task
     */
    @Override
    public TaskResponse stopTask(Long taskId) {
        Task task = getTask(taskId);
        if (task.getStatus().equals(TaskStatus.CREATED))
            throw new TaskNotStartedException(ErrorMessages.TASK_TIME_EXCEPTION.formatted("stop", taskId, "Task not started"));
        if (task.getStatus().equals(TaskStatus.INACTIVE))
            throw new TaskAlreadyStopped(ErrorMessages.TASK_TIME_EXCEPTION.formatted("stop", taskId, "Task already stopped"));
        try {
            task.setStatus(TaskStatus.INACTIVE);
            task.setEndTime(LocalDateTime.now());
            Task save = taskRepository.save(task);
            return taskMapper.toDto(save);
        } catch (Exception ex) {
            throw new TaskTimeException(ErrorMessages.TASK_TIME_EXCEPTION.formatted("stop", taskId, ex.getMessage()));
        }
    }

    /**
     * Retrieves a task by its ID.
     * <p>
     * This method finds a task by its ID and returns it.
     * </p>
     *
     * @param taskId the ID of the task to retrieve
     * @return the task as a {@link Task}
     * @throws TaskNotFoundException if no task is found with the given ID
     */
    private Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND.formatted(taskId)));
    }
}