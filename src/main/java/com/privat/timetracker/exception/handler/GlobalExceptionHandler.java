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

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getMessage()), HttpStatus.NOT_FOUND.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> exceptionsMessages = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        ErrorResponse errorResponse = new ErrorResponse(exceptionsMessages, HttpStatus.BAD_REQUEST.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {TaskAlreadyStarted.class, TaskAlreadyStopped.class, TaskNotStartedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleTaskBadRequest(RuntimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getMessage()), HttpStatus.BAD_REQUEST.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TaskTimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleTaskTimeException(TaskTimeException ex){
        ErrorResponse errorResponse = new ErrorResponse(List.of(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(List.of(ErrorMessages.UNEXPECTED_ERROR.formatted(ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR.value(), getTimestamp());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static String getTimestamp() {
        return Timestamp.from(Instant.now()).toString();
    }
}
