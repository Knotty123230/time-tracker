package com.privat.timetracker.exception.exceptions;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String string) {
        super(string);
    }
}
