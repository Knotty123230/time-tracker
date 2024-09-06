package com.privat.timetracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.exception.exceptions.TaskNotFoundException;
import com.privat.timetracker.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void testCreateTask() throws Exception {
        TaskRequest taskRequest = new TaskRequest("New Task", "Task Description");
        TaskResponse taskResponse = new TaskResponse(1L, "New Task", "Task Description", null, null, null);

        when(taskService.createTask(any(TaskRequest.class))).thenReturn(taskResponse);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.description").value("Task Description"));
    }

    @Test
    public void testEditTask() throws Exception {
        TaskRequest taskRequest = new TaskRequest("Updated Task", "Updated Description");
        TaskResponse taskResponse = new TaskResponse(1L, "Updated Task", "Updated Description", null, null, null);

        when(taskService.updateTask(anyLong(), any(TaskRequest.class))).thenReturn(taskResponse);

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        TaskResponse taskResponse = new TaskResponse(1L, "Task 1", "Description 1", null, null, null);
        List<TaskResponse> taskResponses = Collections.singletonList(taskResponse);

        when(taskService.getAllTasks()).thenReturn(taskResponses);

        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[0].description").value("Description 1"));
    }

    @Test
    public void testGetTask() throws Exception {
        TaskResponse taskResponse = new TaskResponse(1L, "Task 1", "Description 1", null, null, null);

        when(taskService.getTask(anyLong())).thenReturn(taskResponse);

        mockMvc.perform(get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Task 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    public void testCreateTask_ValidationError() throws Exception {
        TaskRequest invalidTaskRequest = new TaskRequest("", "");


        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidTaskRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.length()").value(4));
    }

    @Test
    public void testEditTask_NotFound() throws Exception {
        TaskRequest taskRequest = new TaskRequest("Updated Task", "Updated Description");

        when(taskService.updateTask(anyLong(), any(TaskRequest.class))).thenThrow(new TaskNotFoundException("Task not found"));

        mockMvc.perform(put("/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(taskRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found"));
    }

    @Test
    public void testGetTask_NotFound() throws Exception {
        when(taskService.getTask(anyLong())).thenThrow(new TaskNotFoundException("Task not found"));

        mockMvc.perform(get("/tasks/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found"));
    }
}