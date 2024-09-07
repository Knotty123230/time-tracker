package com.privat.timetracker.mapping;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Mapper interface for converting between {@link TaskRequest}, {@link TaskResponse}, and {@link Task} entities.
 * This interface uses MapStruct to automatically generate implementations for mapping operations.
 * <p>
 * The {@link Mapper} annotation indicates that this is a MapStruct mapper. The component model is set to SPRING
 * to allow Spring to handle dependency injection for this mapper.
 * </p>
 */
@Mapper(componentModel = ComponentModel.SPRING)
public interface TaskMapper {

    /**
     * Converts a {@link TaskRequest} to a {@link Task} entity.
     *
     * @param taskRequest the {@link TaskRequest} to convert
     * @return the corresponding {@link Task} entity
     */
    @Mappings(value = {
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description")
    })
    Task toEntity(TaskRequest taskRequest);

    /**
     * Converts a {@link Task} entity to a {@link TaskResponse} DTO.
     *
     * @param task the {@link Task} entity to convert
     * @return the corresponding {@link TaskResponse} DTO
     */
    @Mappings(value = {
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "startTime", target = "startTime"),
            @Mapping(source = "endTime", target = "endTime"),
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(source = "updatedAt", target = "editedAt"),
            @Mapping(target = "duration", expression = "java(formatDuration(task.getStartTime(), task.getEndTime()))")
    })
    TaskResponse toDto(Task task);

    /**
     * Updates a {@link Task} entity with data from a {@link TaskRequest}.
     *
     * @param taskRequest the {@link TaskRequest} containing the new data
     * @param task        the {@link Task} entity to update
     */
    @Mappings(value = {
            @Mapping(target = "title", source = "taskRequest.title"),
            @Mapping(target = "description", source = "taskRequest.description")
    })
    Task updateTaskFromRequest(TaskRequest taskRequest, @MappingTarget Task task);

    /**
     * Formats the duration between {@link LocalDateTime} start and end times as a string in the format "HH:mm:ss".
     * If the end time is null, the current time is used.
     *
     * @param startTime the start time of the duration
     * @param endTime   the end time of the duration, or null to use the current time
     * @return a string representing the duration in "HH:mm:ss" format
     */
    default String formatDuration(LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime == null) endTime = LocalDateTime.now();
        if (startTime != null) {
            Duration duration = Duration.between(startTime, endTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
        return "00:00:00";
    }
}