package com.privat.timetracker.repository;

import com.privat.timetracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
