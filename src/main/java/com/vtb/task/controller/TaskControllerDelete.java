package com.vtb.task.controller;

import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.service.TaskServiceDelete;
import com.vtb.task.aspect.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tasks/delete")
public class TaskControllerDelete {

    @Autowired
    private TaskServiceDelete service;

    @Audit
    @DeleteMapping("/all")
    public Map<String, String> deleteAllTasks() throws TaskNotFoundException {
        return service.deleteAllTasks();
    }

    @Audit
    @DeleteMapping("/id/{id}")
    public Map<String, String> deleteTaskById (@PathVariable Long id) throws TaskNotFoundException {
        return service.deleteTaskById(id);
    }
}
