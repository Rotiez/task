package com.vtb.task.controller;

import com.vtb.task.entity.Task;
import com.vtb.task.service.TaskServiceGet;
import com.vtb.task.validation.interfaces.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskControllerGet {
    @Autowired
    private TaskServiceGet service;

    @Audit("GET")
    @GetMapping("/get/all")
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok(service.getAllTasks());
    }
}
