package com.privat.timetracker.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String string) {
        super(string);
    }
}
