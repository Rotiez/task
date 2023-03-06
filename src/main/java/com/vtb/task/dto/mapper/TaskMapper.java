package com.vtb.task.dto.mapper;

import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper MAPPER = Mappers.getMapper(TaskMapper.class);
    Task fromRequestToEntity(TaskRequest taskRequest);
    TaskResponse fromEntityToResponse(Task task);
}
