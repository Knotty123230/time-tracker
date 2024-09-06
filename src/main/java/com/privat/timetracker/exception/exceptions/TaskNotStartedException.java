package com.privat.timetracker.exception.exceptions;

public class TaskNotStartedException extends RuntimeException {
    public TaskNotStartedException(String message) {
        super(message);
    }
}
