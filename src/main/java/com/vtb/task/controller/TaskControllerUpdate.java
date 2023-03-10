package com.vtb.task.controller;

import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.service.TaskServiceUpdate;
import com.vtb.task.aspect.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks/update")
public class TaskControllerUpdate {
    //Сервис
    @Autowired
    private TaskServiceUpdate service;

    //Обращение к сервису на обновление 'задачи' при получениии PUT запроса с телом и 'id'
    @Audit
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask (@RequestBody TaskRequest taskRequest, @PathVariable("id") Long id){
        return ResponseEntity.ok(service.updateTask(taskRequest, id));
    }
}
