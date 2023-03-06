package com.vtb.task.controller;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.service.TaskServiceGetById;
import com.vtb.task.validation.interfaces.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskControllerGetById {
    @Autowired
    private TaskServiceGetById service;

    @Audit("GET")
    @GetMapping("get/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) throws TaskNotFoundException {
        return ResponseEntity.ok(service.getTask(id));
    }
}
