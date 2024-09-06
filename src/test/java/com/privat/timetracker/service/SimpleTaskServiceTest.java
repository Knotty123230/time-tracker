package com.privat.timetracker.service;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.entity.Task;
import com.privat.timetracker.entity.TaskStatus;
import com.privat.timetracker.exception.constants.ErrorMessages;
import com.privat.timetracker.exception.exceptions.TaskNotFoundException;
import com.privat.timetracker.mapping.TaskMapper;
import com.privat.timetracker.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimpleTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private SimpleTaskService simpleTaskService;

    @BeforeEach
    public void setUp() {
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        try {
            autoCloseable.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreateTask() {
        TaskRequest request = new TaskRequest("Test Title", "Test Description");
        Task task = new Task();
        Task savedTask = new Task();

        when(taskMapper.toEntity(request)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(savedTask);
        when(taskMapper.toDto(savedTask)).thenReturn(new TaskResponse(
                1L, "Test Title", "Test Description", TaskStatus.CREATED, LocalDateTime.MIN, LocalDateTime.MIN
        ));

        TaskResponse response = simpleTaskService.createTask(request);

        assertNotNull(response);
        assertEquals("Test Title", response.title());
        assertEquals("Test Description", response.description());
        assertEquals(TaskStatus.CREATED, response.status());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testCreateTaskWithNullRequest() {
        assertThrows(NullPointerException.class, () -> simpleTaskService.createTask(null));
    }

    @Test
    public void testUpdateTask() {
        Long taskId = 1L;
        TaskRequest request = new TaskRequest("Updated Title", "Updated Description");
        Task existingTask = new Task();
        Task updatedTask = new Task();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskMapper.updateTaskFromRequest(request, existingTask)).thenReturn(updatedTask);
        when(taskRepository.save(updatedTask)).thenReturn(updatedTask);
        when(taskMapper.toDto(updatedTask)).thenReturn(new TaskResponse(
                taskId, "Updated Title", "Updated Description", TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.now()
        ));

        TaskResponse response = simpleTaskService.updateTask(taskId, request);

        assertNotNull(response);
        assertEquals("Updated Title", response.title());
        assertEquals("Updated Description", response.description());
        assertEquals(TaskStatus.CREATED, response.status());
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(updatedTask);
    }

    @Test
    public void testUpdateTaskNotFound() {
        Long taskId = 1L;
        TaskRequest request = new TaskRequest("Updated Title", "Updated Description");

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskNotFoundException thrown = assertThrows(TaskNotFoundException.class, () -> {
            simpleTaskService.updateTask(taskId, request);
        });

        assertEquals(ErrorMessages.TASK_NOT_FOUND.formatted(taskId), thrown.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void testUpdateTaskWithNullRequest() {
        Long taskId = 1L;
        when(taskRepository.findById(any())).thenReturn(Optional.of(new Task()));

        assertThrows(NullPointerException.class, () -> simpleTaskService.updateTask(taskId, null));
    }

    @Test
    public void testGetTask() {
        Long taskId = 1L;
        Task task = new Task();
        TaskResponse expectedResponse = new TaskResponse(
                taskId, "Title", "Description", TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.now()
        );

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(expectedResponse);

        TaskResponse response = simpleTaskService.getTask(taskId);

        assertNotNull(response);
        assertEquals("Title", response.title());
        assertEquals("Description", response.description());
        assertEquals(TaskStatus.CREATED, response.status());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void testGetTaskNotFound() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        TaskNotFoundException thrown = assertThrows(TaskNotFoundException.class, () -> {
            simpleTaskService.getTask(taskId);
        });

        assertEquals(ErrorMessages.TASK_NOT_FOUND.formatted(taskId), thrown.getMessage());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void testGetAllTasks() {
        Task task = new Task();
        TaskResponse response = new TaskResponse(
                1L, "Title", "Description", TaskStatus.CREATED, LocalDateTime.now(), LocalDateTime.now()
        );

        when(taskRepository.findAll()).thenReturn(Collections.singletonList(task));
        when(taskMapper.toDto(task)).thenReturn(response);

        List<TaskResponse> responses = simpleTaskService.getAllTasks();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Title", responses.get(0).title());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllTasksEmpty() {
        when(taskRepository.findAll()).thenReturn(Collections.emptyList());

        List<TaskResponse> responses = simpleTaskService.getAllTasks();

        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;

        when(taskRepository.existsById(taskId)).thenReturn(true);

        simpleTaskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void testDeleteTaskNotFound() {
        Long taskId = 1L;

        when(taskRepository.existsById(taskId)).thenReturn(false);

        TaskNotFoundException thrown = assertThrows(TaskNotFoundException.class, () -> {
            simpleTaskService.deleteTask(taskId);
        });

        assertEquals(ErrorMessages.TASK_NOT_FOUND.formatted(taskId), thrown.getMessage());
        verify(taskRepository, times(1)).existsById(taskId);
    }
}