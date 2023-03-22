package com.vtb.task.controller;

import com.vtb.task.dto.mapper.TaskMapper;
import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.service.TaskServiceFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;
    @Mock
    private TaskServiceFacade serviceFacade;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        taskController = new TaskController(serviceFacade);
    }

//===========================================================================================

    @Test
    public void getAllTasks_success() throws TaskNotFoundException {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task());
        tasks.add(new Task());
        when(serviceFacade.getAllTasks()).thenReturn(tasks);
        ResponseEntity<List<Task>> response = taskController.getAllTasks();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
    }

    @Test
    public void getAllTasks_failure() throws TaskNotFoundException {
        when(serviceFacade.getAllTasks()).thenThrow(TaskNotFoundException.class);
        assertThrows(TaskNotFoundException.class, () -> taskController.getAllTasks());
    }

    @Test
    public void getTaskById_success() throws TaskNotFoundException {
        Task task = new Task();
        task.setTaskId(1L);
        TaskResponse taskResponse = TaskMapper.MAPPER.fromEntityToResponse(task);
        when(serviceFacade.getTaskById(1L)).thenReturn(taskResponse);
        ResponseEntity<TaskResponse> response = taskController.getTaskById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskResponse, response.getBody());
    }

    @Test
    public void getTaskById_failure() throws TaskNotFoundException {
        when(serviceFacade.getTaskById(1L)).thenThrow(TaskNotFoundException.class);
        assertThrows(TaskNotFoundException.class, () -> taskController.getTaskById(1L));
    }

    @Test
    public void getTaskByName_success() throws TaskNotFoundException {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setName("Task1");
        tasks.add(task);
        when(serviceFacade.getTaskByName("Task1")).thenReturn(tasks);
        ResponseEntity<List<Task>> response = taskController.getTaskByName("Task1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tasks, response.getBody());
    }

    @Test
    public void getTaskByName_failure() throws TaskNotFoundException {
        when(serviceFacade.getTaskByName("Task")).thenThrow(TaskNotFoundException.class);
        assertThrows(TaskNotFoundException.class, () -> taskController.getTaskByName("Task"));
    }

//===========================================================================================

    @Test
    public void saveTask_success() {
        TaskRequest taskRequest = TaskRequest.builder()
                .name("Test task")
                .type("Test type")
                .status("NEW")
                .owner("Test owner")
                .executor("Test executor")
                .build();
        TaskResponse taskResponse = TaskResponse.builder()
                .name("Test task")
                .type("Test type")
                .status("NEW")
                .owner("Test owner")
                .executor("Test executor")
                .build();

        when(serviceFacade.saveTask(taskRequest)).thenReturn(taskResponse);

        ResponseEntity<TaskResponse> responseEntity = taskController.saveTask(taskRequest);

        verify(serviceFacade, times(1)).saveTask(taskRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(taskResponse, responseEntity.getBody());
    }

//===========================================================================================

    @Test
    public void updateTask_success() throws TaskNotFoundException {
        Task task = new Task();
        task.setTaskId(1L);
        task.setName("Task");
        task.setType("Type");
        task.setStatus("Status");
        task.setOwner("Owner");
        task.setExecutor("Executor");

        Long id = 1L;
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Test Task");
        taskRequest.setType("Task Type");
        taskRequest.setStatus("Task Status");
        taskRequest.setOwner("Task Owner");
        taskRequest.setExecutor("Task Executor");

        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setName(taskRequest.getName());
        taskResponse.setType(taskRequest.getType());
        taskResponse.setStatus(taskRequest.getStatus());
        taskResponse.setOwner(taskRequest.getOwner());
        taskResponse.setExecutor(taskRequest.getExecutor());

        when(serviceFacade.updateTask(taskRequest, id)).thenReturn(taskResponse);

        ResponseEntity<TaskResponse> response = taskController.updateTask(taskRequest, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskResponse, response.getBody());
    }

    @Test
    public void updateTask_Failure() throws Exception {
        Long id = 1L;
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Test Task");
        taskRequest.setType("Task Type");
        taskRequest.setStatus("Task Status");
        taskRequest.setOwner("Task Owner");
        taskRequest.setExecutor("Task Executor");

        when(serviceFacade.updateTask(taskRequest, id)).thenThrow(new TaskNotFoundException("Задача не найдена"));

        try {
            taskController.updateTask(taskRequest, id);
        } catch (TaskNotFoundException ex) {
            assertEquals("Задача не найдена", ex.getMessage());
        }
    }

//===========================================================================================

    @Test
    public void deleteTaskById_success() throws TaskNotFoundException{
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("message", "задача с id: 1 успешно удалена");
        when(serviceFacade.deleteTaskById(1L)).thenReturn(expectedResult);

        Map<String, String> actualResult = taskController.deleteTaskById(1L);

        assertEquals(expectedResult, actualResult);
        verify(serviceFacade, times(1)).deleteTaskById(1L);

    }

    @Test
    public void deleteTaskById_failure() throws TaskNotFoundException {
        when(serviceFacade.deleteTaskById(1L)).thenThrow(TaskNotFoundException.class);
        assertThrows(TaskNotFoundException.class, () -> taskController.deleteTaskById(1L));
    }

    @Test
    public void deleteAllTasks() throws TaskNotFoundException{
        Map<String, String> expectedResult = new HashMap<>();
        expectedResult.put("message", "Все задачи успешно удалены");
        when(serviceFacade.deleteAllTasks()).thenReturn(expectedResult);

        Map<String, String> actualResult = taskController.deleteAllTasks();

        assertEquals(expectedResult, actualResult);
        verify(serviceFacade, times(1)).deleteAllTasks();

    }

    @Test
    public void deleteAllTasks_failure() throws TaskNotFoundException {
        when(serviceFacade.deleteAllTasks()).thenThrow(TaskNotFoundException.class);
        assertThrows(TaskNotFoundException.class, () -> taskController.deleteAllTasks());
    }
}
