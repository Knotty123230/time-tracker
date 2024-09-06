package com.privat.timetracker.exception.handler;

import com.privat.timetracker.exception.constants.ErrorMessages;
import com.privat.timetracker.exception.dto.ErrorResponse;
import com.privat.timetracker.exception.exceptions.TaskNotFoundException;
import com.privat.timetracker.exception.exceptions.TaskTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

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
    public void testHandleTaskNotFoundException() {
        TaskNotFoundException ex = new TaskNotFoundException("Task not found");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleTaskNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Task not found", response.getBody().message().get(0));
    }

    @Test
    public void testHandleValidationErrors() {
        org.springframework.validation.BindingResult bindingResult = mock(org.springframework.validation.BindingResult.class);

        FieldError fieldError = new FieldError("objectName", "field", "error message");


        when(bindingResult.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("error message", response.getBody().message().get(0));
    }

    @Test
    public void testHandleTaskTimeException() {
        TaskTimeException ex = new TaskTimeException("Task time error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleTaskTimeException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Task time error", response.getBody().message().get(0));
    }

    @Test
    public void testHandleGenericException() {
        Exception ex = new Exception("Generic error");

        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorMessages.UNEXPECTED_ERROR.formatted("Generic error"), response.getBody().message().get(0));
    }

}