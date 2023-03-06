package com.vtb.task.service;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.repository.TaskRepository;
import com.vtb.task.validation.interfaces.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceGetById {
    @Autowired
    private TaskRepository repository;


    public Task getTask(Long id) throws TaskNotFoundException {
        Task task = repository.findByTaskId(id);
        if(task!=null){
            return task;
        }else{
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
    }
}
