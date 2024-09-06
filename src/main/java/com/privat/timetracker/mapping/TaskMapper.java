package com.privat.timetracker.mapping;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TaskMapper {
    @Mappings(value = {
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description")
    })
    Task toEntity(TaskRequest taskRequest);

    @Mappings(value = {
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "status", target = "status"),
            @Mapping(source = "startTime", target = "startTime"),
            @Mapping(source = "endTime", target = "endTime")
    })
    TaskResponse toDto(Task task);

    @Mappings(value = {
            @Mapping(target = "title", source = "taskRequest.title"),
            @Mapping(target = "description", source = "taskRequest.description")
    })
    Task updateTaskFromRequest(TaskRequest taskRequest, @MappingTarget Task task);
}