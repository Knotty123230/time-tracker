package com.privat.timetracker.exception.exceptions;

public class TaskAlreadyStopped extends RuntimeException {
    public TaskAlreadyStopped(String formatted) {
        super(formatted);
    }
}
