package com.vtb.task.service;

import com.vtb.task.entity.Task;
import com.vtb.task.repository.TaskRepository;
import org.springframework.stereotype.Service;

/**
 * Сервис с методом
 * {@link #saveTask(Task, TaskRepository)}
 */
@Service
public class TaskServiceSave {
    /**
     * Метод для сохранения задачи
     * @param task объект {@link Task}
     * @param taskRepository репозиторий
     * @return возвращает сохраненный объект {@link Task}
     */
    public Task saveTask(Task task, TaskRepository taskRepository) {
        taskRepository.save(task);
        return task;
    }
}
