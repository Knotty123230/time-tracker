package com.privat.timetracker.exception.exceptions;

public class TaskTimeException extends RuntimeException {
    public TaskTimeException(String start) {
        super(start);
    }
}
