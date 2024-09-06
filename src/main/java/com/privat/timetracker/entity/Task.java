package com.privat.timetracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "task")
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 500)
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(startTime, task.startTime) && Objects.equals(endTime, task.endTime) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, startTime, endTime, status);
    }
}
