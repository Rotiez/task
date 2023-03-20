package com.vtb.task.service;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис с методами
 * {@link #deleteAllTasks(TaskRepository)}
 * {@link  #deleteTaskById(Long, TaskRepository)}
 */
@Service
@Transactional
public class TaskServiceDelete {
    /**
     * Метод для удаления всех задач
     * @param repo репозиторий
     * @return возвращает {@link Map} с {@link String} ключом и {@link String} значением
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    public Map<String, String> deleteAllTasks(TaskRepository repo) throws TaskNotFoundException {
        Map<String, String> response = new HashMap<>();
        if (repo.findAll().isEmpty()){
            throw new TaskNotFoundException("Нет задач");
        }else{
            repo.deleteAll();
            response.put("message", "Все задачи успешно удалены");
            return response;
        }
    }

    /**
     * Метод для удаления задачи по идентификатору
     * @param id идентификатор {@link Task}
     * @param repo репозиторий
     * @return возвращает {@link Map} с {@link String} ключом и {@link String} значением
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    public Map<String, String> deleteTaskById(Long id, TaskRepository repo) throws TaskNotFoundException {
        Map<String, String> response = new HashMap<>();
        if(repo.findById(id).isEmpty()){
            throw new TaskNotFoundException("Задача с id: '" + id + "' не найдена!");
        }else{
            repo.deleteById(id);
            response.put("message", "Задача с id: '" + id + "' успешно удалена.");
            return response;
        }
    }
}
