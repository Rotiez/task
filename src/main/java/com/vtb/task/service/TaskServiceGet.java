package com.vtb.task.service;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceGet {
    @Autowired
    private TaskRepository repository;

    public List<Task> getAllTasks() throws TaskNotFoundException {

        if (repository.findAll().isEmpty()){
            throw new TaskNotFoundException("Нет задач");
        }else{
            return repository.findAll();
        }
    }

    public Optional<Task> getTaskById(Long id) throws TaskNotFoundException {
        if(repository.findById(id).isPresent()){
            return repository.findById(id);
        }else{
            throw new TaskNotFoundException("Задача с id: '" + id + "' не найдена!");
        }
    }

    public List<Task> getTaskByName(String name) throws TaskNotFoundException {
        if(repository.findByName(name).isEmpty()){
            throw new TaskNotFoundException("Задача с name: '" + name + "' не найдена!");
        }else{
            return repository.findByName(name);
        }
    }
}
