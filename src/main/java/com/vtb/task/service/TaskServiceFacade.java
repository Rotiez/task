package com.vtb.task.service;

import com.vtb.task.dto.mapper.TaskMapper;
import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *  Фасад для сервисов
 *  {@link TaskServiceSave},
 *  {@link TaskServiceGet},
 *  {@link TaskServiceUpdate},
 *  {@link TaskServiceDelete}
 */
@Component
@RequiredArgsConstructor
public class TaskServiceFacade {

    private final TaskRepository taskRepository;
    private final TaskServiceDelete serviceDelete;
    private final TaskServiceSave serviceSave;
    private final TaskServiceGet serviceGet;
    private final TaskServiceUpdate serviceUpdate;

    /**
     * Метод для сохранения задачи, в котором маппится запрос ({@link TaskRequest}) в сущность ({@link Task}),
     * после чего вызывается метод сервиса {@link TaskServiceSave#saveTask(Task, TaskRepository)},
     * и результат выполнения метода маппится в ответ {@link TaskResponse}
     * @param taskRequest объект запроса {@link TaskRequest}
     * @return возвращает объект {@link TaskResponse}
     */
    public TaskResponse saveTask(TaskRequest taskRequest){
        Task task = TaskMapper.MAPPER.fromRequestToEntity(taskRequest);
        Task savedTask = serviceSave.saveTask(task, taskRepository);
        return TaskMapper.MAPPER.fromEntityToResponse(savedTask);
    }

    /**
     * Метод для обновления задачи, в котором маппится запрос ({@link TaskRequest}) в сущность ({@link Task}),
     * после чего вызывается метод сервиса {@link TaskServiceUpdate#updateTask(Task, Long, TaskRepository)},
     * и результат выполнения метода маппится в ответ {@link TaskResponse}
     * @param taskRequest объект запроса {@link TaskRequest}
     * @param id идентификатор задачи
     * @return возвращает объект {@link TaskResponse}
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    public TaskResponse updateTask (TaskRequest taskRequest, Long id) throws TaskNotFoundException{
        Task task = TaskMapper.MAPPER.fromRequestToEntity(taskRequest);
        Task updatedTask = serviceUpdate.updateTask(task, id, taskRepository);
        return TaskMapper.MAPPER.fromEntityToResponse(updatedTask);
    }

    /**
     * Метод для получения списка задач, который обращается к методу {@link TaskServiceGet#getAllTasks(TaskRepository)}
     * @return возвращает {@link List<Task>} задач ({@link Task})
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    public List<Task> getAllTasks() throws TaskNotFoundException {
        return serviceGet.getAllTasks(taskRepository);
    }

    /**
     * Метод для получения задачи по идентификатору, который обращается к методу {@link TaskServiceGet#getTaskById(Long, TaskRepository)}
     * и маппит найденную задачу в {@link TaskResponse}
     * @param id идентификатор задачи
     * @return возвращает объект {@link TaskResponse}
     * @throws TaskNotFoundException исключение в случае отсутствия объекта {@link Task} в репозитории
     */
    public TaskResponse getTaskById(Long id) throws TaskNotFoundException {
        Task foundTask = serviceGet.getTaskById(id, taskRepository);
        return TaskMapper.MAPPER.fromEntityToResponse(foundTask);
    }

    /**
     * Метод для получения задач по названию, который обращается к методу {@link TaskServiceGet#getTaskByName(String, TaskRepository)}
     * @param name название задачи
     * @return возвращает {@link List<Task>} задач ({@link Task})
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    public List<Task> getTaskByName(String name) throws TaskNotFoundException {
        return serviceGet.getTaskByName(name, taskRepository);
    }

    /**
     * Метод для удаления всех задач, который обращается к методу {@link TaskServiceDelete#deleteAllTasks(TaskRepository)}
     * @return возвращает возвращает {@link Map} с {@link String} ключом и {@link String} значением
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    public Map<String, String> deleteAllTasks() throws TaskNotFoundException {
        return serviceDelete.deleteAllTasks(taskRepository);
    }

    /**
     * Метод для удаления задачи по идентификатору, который обращается к методу {@link TaskServiceDelete#deleteTaskById(Long, TaskRepository)}
     * @param id идентификатор задачи
     * @return возвращает возвращает {@link Map} с {@link String} ключом и {@link String} значением
     * @throws TaskNotFoundException исключение в случае отсутствия {@link Task} в репозитории
     */
    public Map<String, String> deleteTaskById(Long id) throws TaskNotFoundException {
        return serviceDelete.deleteTaskById(id, taskRepository);
    }
}



