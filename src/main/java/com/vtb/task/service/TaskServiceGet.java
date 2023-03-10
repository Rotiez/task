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

    //Обращается к репозиторию для возвращения всех задач
    //Предварительно проверяется наличие хоть одной задачи,
    //В противном случае кидается исключение не найденной задачи с сообщением 'нет задач'
    public List<Task> getAllTasks() throws TaskNotFoundException {

        if (repository.findAll().isEmpty()){
            throw new TaskNotFoundException("Нет задач");
        }else{
            return repository.findAll();
        }
    }

    //Обращается к репозиторию для возвращения задачи с определенным 'id'
    //Предварительно проверяется наличие задачи c данным 'id',
    //В противном случае кидается исключение не найденной задачи с этим 'id'
    public Optional<Task> getTaskById(Long id) throws TaskNotFoundException {
        if(repository.findById(id).isPresent()){
            return repository.findById(id);
        }else{
            throw new TaskNotFoundException("Задача с id: '" + id + "' не найдена!");
        }
    }

    //Обращается к репозиторию для возвращения задачи с определенным названием
    //Предварительно проверяется наличие задачи c данным названием,
    //В противном случае кидается исключение не найденной задачи с этим названиме
    public List<Task> getTaskByName(String name) throws TaskNotFoundException {
        if(repository.findByName(name).isEmpty()){
            throw new TaskNotFoundException("Задача с name: '" + name + "' не найдена!");
        }else{
            return repository.findByName(name);
        }
    }
}
