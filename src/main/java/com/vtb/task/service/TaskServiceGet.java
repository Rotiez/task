package com.vtb.task.service;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис c методами
 * {@link #getAllTasks(TaskRepository)},
 * {@link #getTaskById(Long, TaskRepository)},
 * {@link #getTaskByName(String, TaskRepository)}
 */
@Service
@Transactional
public class TaskServiceGet {

    /**
     * Метод для получения списка всех задач
     * @param repo  репозиторий
     * @return возвращает список объектов {@link Task}
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    public List<Task> getAllTasks(TaskRepository repo) throws TaskNotFoundException {
        if (repo.findAll().isEmpty()){
            throw new TaskNotFoundException("Нет задач");
        }else{
            return repo.findAll();
        }
    }

    /**
     * Метод для получения задачи по ее идентификатору
     * @param id   идентификатор сущности {@link Task}
     * @param repo репозиторий
     * @return возвращает объект {@link Task}
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    public Task getTaskById(Long id, TaskRepository repo) throws TaskNotFoundException {
        if(repo.findById(id).isEmpty()){
            throw new TaskNotFoundException("Задача с id: '" + id + "' не найдена!");
        }else{
            return repo.findById(id).orElse(null);
        }
    }

    /**
     * Метод для получения задачи по ее названию
     * @param name название сущности {@link Task}
     * @param repo репозиторий
     * @return возвращает список объектов {@link Task}
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} с указанным названием в репозитории
     */
    public List<Task> getTaskByName(String name, TaskRepository repo) throws TaskNotFoundException {
        if(repo.findByName(name).isEmpty()){
            throw new TaskNotFoundException("Задача с name: '" + name + "' не найдена!");
        }else{
            return repo.findByName(name);
        }
    }
}
