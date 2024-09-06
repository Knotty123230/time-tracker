package com.privat.timetracker.exception.exceptions;

public class TaskAlreadyStarted extends RuntimeException {
    public TaskAlreadyStarted(String formatted) {
        super(formatted);
    }
}
