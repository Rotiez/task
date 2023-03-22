package com.vtb.task.controller;

import com.vtb.task.aspect.Audit;
import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.service.TaskServiceFacade;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Контроллер, передающий валидные запросы на {@link TaskServiceFacade}
 */
@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
@Validated
public class TaskController {

    private final TaskServiceFacade service;

//====================================================================================================================

    /**
     * @return возвращает {@link Map} с {@link String} ключом и {@link String} значением
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    @Audit
    @DeleteMapping("/delete/all")
    public Map<String, String> deleteAllTasks() throws TaskNotFoundException {
        return service.deleteAllTasks();
    }

    /**
     * @param id идентификатор задачи
     * @return возвращает {@link Map} с {@link String} ключом и {@link String} значением
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    @Audit
    @DeleteMapping("/delete/id/{id}")
    public Map<String, String> deleteTaskById(@PathVariable Long id) throws TaskNotFoundException {
        return service.deleteTaskById(id);
    }

//====================================================================================================================

    /**
     * @return возвращает {@link ResponseEntity} с {@link List} задач ({@link Task})
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    @Audit
    @GetMapping("/get/all")
    public ResponseEntity<List<Task>> getAllTasks() throws TaskNotFoundException {
        return ResponseEntity.ok(service.getAllTasks());
    }

    /**
     * @param id идентификатор задачи
     * @return возвращает {@link ResponseEntity} с {@link TaskResponse}
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    @Audit
    @GetMapping("/get/id/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) throws TaskNotFoundException {
        return ResponseEntity.ok(service.getTaskById(id));
    }

    /**
     * @param name название задачи
     * @return возвращает {@link ResponseEntity} с {@link List} задач ({@link Task})
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    @Audit
    @GetMapping("/get/name/{name}")
    public ResponseEntity<List<Task>> getTaskByName(@PathVariable String name) throws TaskNotFoundException {
        return ResponseEntity.ok(service.getTaskByName(name));
    }

//====================================================================================================================

    /**
     * @param taskRequest объект {@link TaskRequest}
     * @return возвращает {@link ResponseEntity} с {@link TaskResponse}
     */
    @Audit
    @PostMapping("/save")
    public ResponseEntity<TaskResponse> saveTask(@Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.ok(service.saveTask(taskRequest));
    }

//====================================================================================================================

    /**
     * @param taskRequest объект {@link TaskRequest}
     * @param id идентификатор задачи
     * @return возвращает {@link ResponseEntity} с {@link TaskResponse}
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    @Audit
    @PutMapping("/update/{id}")
    public ResponseEntity<TaskResponse> updateTask (@Valid @RequestBody TaskRequest taskRequest, @PathVariable("id") Long id) throws TaskNotFoundException {
        return ResponseEntity.ok(service.updateTask(taskRequest, id));
    }
}
