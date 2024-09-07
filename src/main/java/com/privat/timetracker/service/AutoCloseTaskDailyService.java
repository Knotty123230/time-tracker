package com.privat.timetracker.service;

import com.privat.timetracker.entity.TaskStatus;
import com.privat.timetracker.exception.constants.ErrorMessages;
import com.privat.timetracker.exception.exceptions.TaskTimeException;
import com.privat.timetracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service class responsible for automatically closing tasks at the end of each day.
 * <p>
 * This service implements the {@link AutoClosableTask} interface and is scheduled to run daily at 23:59:59.
 * It marks all active tasks as inactive and sets their end time to the current time.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AutoCloseTaskDailyService implements AutoClosableTask {

    private final TaskRepository taskRepository;

    /**
     * Automatically closes all tasks that are currently active at the end of the day.
     * <p>
     * This method is scheduled to run daily at 23:59:59. It updates the status of all active tasks to
     * {@link TaskStatus#INACTIVE} and sets their end time to the current time.
     * </p>
     * <p>
     * If an exception occurs during the task update, a {@link TaskTimeException} is thrown with a relevant
     * error message.
     * </p>
     */
    @Override
    @Scheduled(cron = "59 59 23 * * *")
    @Transactional
    public void autoCloseTasks() {
        try {
            LocalDateTime now = LocalDateTime.now().withNano(0);
            taskRepository.findAllByStatus(TaskStatus.ACTIVE)
                    .forEach(task -> {
                        task.setEndTime(now);
                        task.setStatus(TaskStatus.INACTIVE);
                        taskRepository.save(task);
                    });
        } catch (Exception ex) {
            throw new TaskTimeException(ErrorMessages.TASK_AUTO_CLOSE_EXCEPTION.formatted(ex.getMessage()));
        }
    }
}