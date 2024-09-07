package com.privat.timetracker.exception.handler;

import com.privat.timetracker.exception.constants.ErrorMessages;
import com.privat.timetracker.exception.dto.ErrorResponse;
import com.privat.timetracker.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * Global exception handler for managing exceptions thrown by the application.
 * This class uses Spring's {@code @ControllerAdvice} to provide centralized exception handling
 * across the entire application, ensuring that proper error responses are sent to clients.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gets the current timestamp in ISO 8601 format.
     *
     * @return the current timestamp as a {@link String}
     */
    private static String getTimestamp() {
        return Timestamp.from(Instant.now()).toString();
    }

    /**
     * Handles {@link TaskNotFoundException} and returns a 404 Not Found status with a detailed error message.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error response and the HTTP status
     */
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getMessage()), HttpStatus.NOT_FOUND.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link MethodArgumentNotValidException} and returns a 400 Bad Request status with validation error messages.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error response and the HTTP status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> exceptionsMessages = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        ErrorResponse errorResponse = new ErrorResponse(exceptionsMessages, HttpStatus.BAD_REQUEST.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles various runtime exceptions related to task operations (e.g., {@link TaskAlreadyStarted},
     * {@link TaskAlreadyStopped}, {@link TaskNotStartedException}) and returns a 400 Bad Request status with a detailed error message.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error response and the HTTP status
     */
    @ExceptionHandler(value = {TaskAlreadyStarted.class, TaskAlreadyStopped.class, TaskNotStartedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTaskBadRequest(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getMessage()), HttpStatus.BAD_REQUEST.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link TaskTimeException} and returns a 500 Internal Server Error status with a detailed error message.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error response and the HTTP status
     */
    @ExceptionHandler(TaskTimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleTaskTimeException(TaskTimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles any other exceptions not specifically handled by the above methods and returns a 500 Internal Server Error status
     * with a generic unexpected error message.
     *
     * @param ex the exception that was thrown
     * @return a {@link ResponseEntity} containing the error response and the HTTP status
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ErrorMessages.UNEXPECTED_ERROR.formatted(ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
