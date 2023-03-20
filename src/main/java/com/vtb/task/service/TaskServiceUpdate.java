package com.vtb.task.service;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис с методом
 * {@link #updateTask(Task, Long, TaskRepository)}
 */
@Service
@Transactional
public class TaskServiceUpdate {
    /**
     * Метод для обновления задачи по ее идентификатору
     * @param task объект {@link Task}
     * @param id идентификатор сущности {@link Task}
     * @param repo репозиторий
     * @return возвращает объект {@link Task}, которая была обновлена
     */
    public Task updateTask (Task task, Long id, TaskRepository repo) throws TaskNotFoundException {
        if(repo.findById(id).isEmpty()){
            throw new TaskNotFoundException("Задача с id: '" + id + "' не найдена!");
        }else{
            task.setTaskId(id);
            repo.save(task);
            return task;
        }
    }
}
