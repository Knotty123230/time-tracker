package com.privat.timetracker.repository;

import com.privat.timetracker.entity.Task;
import com.privat.timetracker.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Iterable<Task> findAllByStatus(TaskStatus taskStatus);
}
