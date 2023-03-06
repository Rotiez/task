package com.vtb.task.controller;

import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.service.TaskServiceSave;
import com.vtb.task.validation.interfaces.Audit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskControllerSave {
    @Autowired
    private TaskServiceSave service;

    @Audit("POST")
    @PostMapping
    public ResponseEntity<TaskResponse> saveTask(@RequestBody TaskRequest taskRequest){
        return ResponseEntity.ok(service.saveTask(taskRequest));
    }
}
