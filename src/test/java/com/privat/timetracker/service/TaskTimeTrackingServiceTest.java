package com.privat.timetracker.service;

import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.entity.Task;
import com.privat.timetracker.entity.TaskStatus;
import com.privat.timetracker.exception.constants.ErrorMessages;
import com.privat.timetracker.exception.exceptions.TaskAlreadyStarted;
import com.privat.timetracker.exception.exceptions.TaskAlreadyStopped;
import com.privat.timetracker.exception.exceptions.TaskNotFoundException;
import com.privat.timetracker.exception.exceptions.TaskTimeException;
import com.privat.timetracker.mapping.TaskMapper;
import com.privat.timetracker.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskTimeTrackingServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskTimeTrackingService taskTimeTrackingService;

    public TaskTimeTrackingServiceTest() {

        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        try {
            autoCloseable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testStartTask_Success() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        task.setStatus(TaskStatus.CREATED);
        TaskResponse taskResponse = getTaskResponse();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskResponse);

        // Act
        TaskResponse result = taskTimeTrackingService.startTask(taskId);

        // Assert
        assertNotNull(result);
        verify(taskRepository).save(task);
        assertEquals(TaskStatus.ACTIVE, task.getStatus());
        assertEquals(LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59)), task.getEndTime());
    }

    private static TaskResponse getTaskResponse() {
        return new TaskResponse(1L, "Title", "description", TaskStatus.CREATED, LocalDateTime.MIN, LocalDateTime.MIN);
    }

    @Test
    public void testStartTask_TaskNotFound() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        TaskNotFoundException thrown = assertThrows(TaskNotFoundException.class, () -> taskTimeTrackingService.startTask(taskId));
        assertEquals(ErrorMessages.TASK_NOT_FOUND.formatted(taskId), thrown.getMessage());
    }

    @Test
    public void testStartTask_TaskAlreadyStarted() {
        Long taskId = 1L;
        Task task = new Task();
        task.setStatus(TaskStatus.ACTIVE);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));


        TaskAlreadyStarted taskTimeException = assertThrows(TaskAlreadyStarted.class, () -> taskTimeTrackingService.startTask(taskId));
        assertTrue(taskTimeException.getMessage().contains("start"));
        assertTrue(taskTimeException.getMessage().contains(taskId.toString()));
    }

    @Test
    public void testStartTask_ExceptionHandling() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        task.setStatus(TaskStatus.CREATED);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        TaskTimeException thrown = assertThrows(TaskTimeException.class, () -> taskTimeTrackingService.startTask(taskId));
        assertTrue(thrown.getMessage().contains("start"));
        assertTrue(thrown.getMessage().contains(taskId.toString()));
    }

    @Test
    public void testStopTask_Success() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        task.setStatus(TaskStatus.ACTIVE);
        TaskResponse taskResponse = getTaskResponse();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskResponse);

        // Act
        TaskResponse result = taskTimeTrackingService.stopTask(taskId);

        // Assert
        assertNotNull(result);
        verify(taskRepository).save(task);
        assertEquals(TaskStatus.INACTIVE, task.getStatus());
        assertEquals(LocalDateTime.now().withNano(0), task.getEndTime().withNano(0));
    }

    @Test
    public void testStopTask_TaskNotFound() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act & Assert
        TaskNotFoundException thrown = assertThrows(TaskNotFoundException.class, () -> taskTimeTrackingService.stopTask(taskId));
        assertEquals(ErrorMessages.TASK_NOT_FOUND.formatted(taskId), thrown.getMessage());
    }

    @Test
    public void testStopTask_TaskAlreadyStopped() {
        Long taskId = 1L;
        Task task = new Task();
        task.setStatus(TaskStatus.INACTIVE);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));


        TaskAlreadyStopped taskTimeException = assertThrows(TaskAlreadyStopped.class, () -> taskTimeTrackingService.stopTask(taskId));
        assertTrue(taskTimeException.getMessage().contains("stop"));
        assertTrue(taskTimeException.getMessage().contains(taskId.toString()));
    }

    @Test
    public void testStopTask_ExceptionHandling() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        task.setStatus(TaskStatus.ACTIVE);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        TaskTimeException thrown = assertThrows(TaskTimeException.class, () -> taskTimeTrackingService.stopTask(taskId));
        assertTrue(thrown.getMessage().contains("stop"));
        assertTrue(thrown.getMessage().contains(taskId.toString()));
    }
}