package com.privat.timetracker.mapping;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.entity.Task;
import com.privat.timetracker.entity.TaskStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    public void testToEntity() {
        TaskRequest request = new TaskRequest("Test Title", "Test Description");

        Task task = taskMapper.toEntity(request);

        assertNotNull(task);
        assertEquals("Test Title", task.getTitle());
        assertEquals("Test Description", task.getDescription());
    }

    @Test
    public void testToDto() {
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Title");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.CREATED);
        task.setStartTime(LocalDateTime.of(2024, 9, 6, 10, 0));
        task.setEndTime(LocalDateTime.of(2024, 9, 6, 12, 0));

        TaskResponse response = taskMapper.toDto(task);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Test Title", response.title());
        assertEquals("Test Description", response.description());
        assertEquals(TaskStatus.CREATED, response.status());
        assertEquals(LocalDateTime.of(2024, 9, 6, 10, 0), response.startTime());
        assertEquals(LocalDateTime.of(2024, 9, 6, 12, 0), response.endTime());
    }

    @Test
    public void testUpdateTaskFromRequest() {
        Task existingTask = new Task();
        existingTask.setTitle("Old Title");
        existingTask.setDescription("Old Description");

        TaskRequest request = new TaskRequest("New Title", "New Description");

        taskMapper.updateTaskFromRequest(request, existingTask);

        assertEquals("New Title", existingTask.getTitle());
        assertEquals("New Description", existingTask.getDescription());
    }
}