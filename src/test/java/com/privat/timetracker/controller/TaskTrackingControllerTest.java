package com.privat.timetracker.controller;

import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.exception.exceptions.TaskNotFoundException;
import com.privat.timetracker.service.TimeTracking;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskTrackingController.class)
public class TaskTrackingControllerTest {


    @MockBean
    private TimeTracking timeTracking;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testStartTask() throws Exception {
        TaskResponse taskResponse = new TaskResponse(1L, "Test Task", null, null, null, null);
        when(timeTracking.startTask(anyLong())).thenReturn(taskResponse);

        mockMvc.perform(post("/api/v1/tasks/1/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    public void testStopTask() throws Exception {
        TaskResponse taskResponse = new TaskResponse(1L, "Test Task", null, null, null, null);

        when(timeTracking.stopTask(anyLong())).thenReturn(taskResponse);

        mockMvc.perform(post("/api/v1/tasks/1/stop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    public void testStartTask_NotFound() throws Exception {
        when(timeTracking.startTask(anyLong())).thenThrow(new TaskNotFoundException("Task not found"));

        mockMvc.perform(post("/api/v1/tasks/999/start")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found"));
    }

    @Test
    public void testStopTask_NotFound() throws Exception {
        when(timeTracking.stopTask(anyLong())).thenThrow(new TaskNotFoundException("Task not found"));

        mockMvc.perform(post("/api/v1/tasks/999/stop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found"));
    }
}