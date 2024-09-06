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

@Service
@RequiredArgsConstructor
public class AutoCloseTaskDailyService implements AutoClosableTask {
    private final TaskRepository taskRepository;

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