package com.vtb.task.service;

import com.vtb.task.dto.mapper.TaskMapper;
import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.entity.Task;
import com.vtb.task.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TaskServiceSave {
    @Autowired
    private TaskRepository repository;

    public TaskResponse saveTask(@Valid TaskRequest taskRequest){
        Task task = TaskMapper.MAPPER.fromRequestToEntity(taskRequest);
        repository.save(task);
        return TaskMapper.MAPPER.fromEntityToResponse(task);
    }
}
