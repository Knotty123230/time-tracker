package com.privat.timetracker.service;

import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.entity.Task;
import com.privat.timetracker.entity.TaskStatus;
import com.privat.timetracker.exception.constants.ErrorMessages;
import com.privat.timetracker.exception.exceptions.TaskNotFoundException;
import com.privat.timetracker.exception.exceptions.TaskTimeException;
import com.privat.timetracker.mapping.TaskMapper;
import com.privat.timetracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskTimeTrackingService implements TimeTracking {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskResponse startTask(Long taskId) {
        try {
            Task task = getTask(taskId);
            task.setStatus(TaskStatus.ACTIVE);
            task.setStartTime(LocalDateTime.now());
            task.setEndTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)));
            Task savedTask = taskRepository.save(task);
            return taskMapper.toDto(savedTask);
        } catch (Exception ex) {
            throw new TaskTimeException(ErrorMessages.TASK_TIME_EXCEPTION.formatted("start", taskId, ex.getMessage()));
        }
    }

    private Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND.formatted(taskId)));
    }

    @Override
    public TaskResponse stopTask(Long taskId) {
        try {
            Task task = getTask(taskId);
            task.setStatus(TaskStatus.INACTIVE);
            task.setEndTime(LocalDateTime.now());
            Task save = taskRepository.save(task);
            return taskMapper.toDto(save);
        }catch (Exception ex){
            throw new TaskTimeException(ErrorMessages.TASK_TIME_EXCEPTION.formatted("stop", taskId, ex.getMessage()));
        }

    }
}
