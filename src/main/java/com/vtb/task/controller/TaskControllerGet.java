package com.vtb.task.controller;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.service.TaskServiceGet;
import com.vtb.task.aspect.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks/get")
public class TaskControllerGet {
    //Сервис
    @Autowired
    private TaskServiceGet service;

    //Обращение к сервису на получение всех 'задач' при получении GET запроса по адресу /tasks/get/all
    @Audit
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getAllTasks() throws TaskNotFoundException {
        return ResponseEntity.ok(service.getAllTasks());
    }

    //Обращение к сервису на получение 'задачи' по 'id'
    //при получении GET запроса по адресу /tasks/get/id/{id}, где {id} - 'id' запрашиваемой задачи
    @Audit
    @GetMapping("/id/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable Long id) throws TaskNotFoundException {
        return ResponseEntity.ok(service.getTaskById(id));
    }

    //Обращение к сервису на получение 'задачи' по названию
    //при получении GET запроса по адресу /tasks/get/name/{name}, где {name} - название запрашиваемой задачи
    @Audit
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Task>> getTaskByName(@PathVariable String name) throws TaskNotFoundException {
        return ResponseEntity.ok(service.getTaskByName(name));
    }
}
