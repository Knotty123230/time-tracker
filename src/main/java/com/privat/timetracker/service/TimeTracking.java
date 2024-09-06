package com.privat.timetracker.service;

public interface TimeTracking {
    boolean startTask(Long taskId);
    boolean stopTask(Long taskId);
}
