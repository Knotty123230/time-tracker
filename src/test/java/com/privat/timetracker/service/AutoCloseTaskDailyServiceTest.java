package com.privat.timetracker.service;

import com.privat.timetracker.entity.Task;
import com.privat.timetracker.entity.TaskStatus;
import com.privat.timetracker.exception.constants.ErrorMessages;
import com.privat.timetracker.exception.exceptions.TaskTimeException;
import com.privat.timetracker.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AutoCloseTaskDailyServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private AutoCloseTaskDailyService autoCloseTaskDailyService;

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
    public void testAutoCloseTasksSuccess() {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Task task = new Task();
        task.setStatus(TaskStatus.ACTIVE);

        when(taskRepository.findAllByStatus(TaskStatus.ACTIVE)).thenReturn(List.of(task));

        autoCloseTaskDailyService.autoCloseTasks();

        assertEquals(TaskStatus.INACTIVE, task.getStatus());
        assertEquals(now, task.getEndTime().withNano(0));
        verify(taskRepository, times(1)).findAllByStatus(TaskStatus.ACTIVE);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void testAutoCloseTasksNoActiveTasks() {
        when(taskRepository.findAllByStatus(TaskStatus.ACTIVE)).thenReturn(Collections.emptyList());

        autoCloseTaskDailyService.autoCloseTasks();

        verify(taskRepository, times(1)).findAllByStatus(TaskStatus.ACTIVE);
        verify(taskRepository, never()).save(any());
    }

    @Test
    public void testAutoCloseTasksExceptionHandling() {
        when(taskRepository.findAllByStatus(TaskStatus.ACTIVE)).thenThrow(new DataAccessException("Database error") {});

        TaskTimeException thrown = assertThrows(TaskTimeException.class, () -> {
            autoCloseTaskDailyService.autoCloseTasks();
        });

        assertEquals(ErrorMessages.TASK_AUTO_CLOSE_EXCEPTION.formatted("Database error"), thrown.getMessage());
        verify(taskRepository, times(1)).findAllByStatus(TaskStatus.ACTIVE);
        verify(taskRepository, never()).save(any());
    }
}