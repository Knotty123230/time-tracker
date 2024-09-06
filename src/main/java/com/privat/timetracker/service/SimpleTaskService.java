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

@Service
@RequiredArgsConstructor
public class SimpleTaskService implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = taskMapper.toEntity(taskRequest);
        task.setStatus(TaskStatus.CREATED);
        task.setStartTime(LocalDateTime.MIN);
        task.setEndTime(LocalDateTime.MIN);
        task.setCreatedAt(LocalDateTime.now());
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

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

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND.formatted(taskId)));
        return taskMapper.toDto(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException(ErrorMessages.TASK_NOT_FOUND.formatted(taskId));
        }
        taskRepository.deleteById(taskId);
    }
}