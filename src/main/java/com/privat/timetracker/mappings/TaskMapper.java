package com.privat.timetracker.mappings;

import com.privat.timetracker.controller.dto.TaskRequest;
import com.privat.timetracker.controller.dto.TaskResponse;
import com.privat.timetracker.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface TaskMapper {


    Task toEntity(TaskRequest taskRequest);

    TaskResponse toDto(Task task);

    void updateTaskFromRequest(TaskRequest taskRequest, Task task);
}