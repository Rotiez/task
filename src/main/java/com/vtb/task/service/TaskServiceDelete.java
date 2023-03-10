package com.vtb.task.service;

import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TaskServiceDelete {

    @Autowired
    private TaskRepository repository;

    //Обращается к репозиторию для удаления всех задач.
    //Предварительно проверяется наличие хоть одной задачи,
    //в противном случае кидается исключение не найденной задачи
    public Map<String, String> deleteAllTasks() throws TaskNotFoundException {
        Map<String, String> response = new HashMap<>();
        if (repository.findAll().isEmpty()){
            throw new TaskNotFoundException("Нет задач");
        }else{
            repository.deleteAll();
            response.put("message", "Все задачи успешно удалены");
            return response;
        }
    }

    //Обращается к репозиторию для удаления задачи по 'id'.
    //Осуществляется проверка наличия задачи c таким 'id',
    //в случае отсутствия такой задачи, кидается исключение не найденной задачи c данным 'id'
    public Map<String, String> deleteTaskById(Long id) throws TaskNotFoundException {
        Map<String, String> response = new HashMap<>();
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
            response.put("message", "Задача с id: '" + id + "' успешно удалена.");
            return response;
        }else{
            throw new TaskNotFoundException("Задача с id: '" + id + "' не найдена!");
        }
    }
}
