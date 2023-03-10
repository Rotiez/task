package com.vtb.task.controller;

import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.exception.UnknownException;
import com.vtb.task.service.TaskServiceSave;
import com.vtb.task.aspect.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks/save")
public class TaskControllerSave {
    @Autowired
    private TaskServiceSave service;

    //Сохранение тела POST запроса по адресу /tasks/save
    @Audit
    @PostMapping
    public ResponseEntity<TaskResponse> saveTask(@RequestBody TaskRequest taskRequest) throws UnknownException {
        return ResponseEntity.ok(service.saveTask(taskRequest));
    }
}
