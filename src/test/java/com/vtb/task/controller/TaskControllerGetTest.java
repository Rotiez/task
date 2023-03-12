package com.vtb.task.controller;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.service.TaskServiceGet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskControllerGetTest {
    @InjectMocks
    private TaskControllerGet controller;

    @Mock
    private TaskServiceGet service;

    @Test
    public void testGetAllTasksSuccess() throws TaskNotFoundException {
        List<Task> tasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setTaskId(1L);
        task1.setName("Task 1");
        task1.setType("Developing");
        task1.setStatus("In process");
        task1.setOwner("Owner 1");
        task1.setExecutor("Executor 1");

        Task task2 = new Task();
        task2.setTaskId(1L);
        task2.setName("Task 2");
        task2.setType("Analytics");
        task2.setStatus("Waiting");
        task2.setOwner("Owner 2");
        task2.setExecutor("Executor 2");

        tasks.add(task1);
        tasks.add(task2);

        when(service.getAllTasks()).thenReturn(tasks);

        ResponseEntity<List<Task>> response = controller.getAllTasks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Task 1", response.getBody().get(0).getName());
        assertEquals("Developing", response.getBody().get(0).getType());
        assertEquals("In process", response.getBody().get(0).getStatus());
        assertEquals("Owner 1", response.getBody().get(0).getOwner());
        assertEquals("Executor 1", response.getBody().get(0).getExecutor());
    }

    @Test
    public void testGetTaskByIdSuccess() throws TaskNotFoundException {
        Task task = new Task();
        task.setTaskId(1L);
        task.setName("Task 1");
        task.setType("Developing");
        task.setStatus("In process");
        task.setOwner("Owner 1");
        task.setExecutor("Executor 1");

        when(service.getTaskById(1L)).thenReturn(Optional.of(task));

        ResponseEntity<Optional<Task>> response = controller.getTaskById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task 1", response.getBody().get().getName());
        assertEquals("Developing", response.getBody().get().getType());
        assertEquals("In process", response.getBody().get().getStatus());
        assertEquals("Owner 1", response.getBody().get().getOwner());
        assertEquals("Executor 1", response.getBody().get().getExecutor());
    }

    @Test
    public void testGetTaskByNameSuccess() throws TaskNotFoundException {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTaskId(1L);
        task.setName("Task 1");
        task.setType("Developing");
        task.setStatus("In process");
        task.setOwner("Owner 1");
        task.setExecutor("Executor 1");
        tasks.add(task);

        when(service.getTaskByName("Task 1")).thenReturn(tasks);

        ResponseEntity<List<Task>> response = controller.getTaskByName("Task 1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Task 1", response.getBody().get(0).getName());
        assertEquals("Developing", response.getBody().get(0).getType());
        assertEquals("In process", response.getBody().get(0).getStatus());
        assertEquals("Owner 1", response.getBody().get(0).getOwner());
        assertEquals("Executor 1", response.getBody().get(0).getExecutor());
    }


    @Test
    public void testGetAllTasksTaskNotFoundException() throws TaskNotFoundException {
        when(service.getAllTasks()).thenThrow(TaskNotFoundException.class);

        Assertions.assertThrows(TaskNotFoundException.class, () -> controller.getAllTasks());
    }

    @Test
    public void testGetTaskByIdTaskNotFoundException() throws TaskNotFoundException {
        when(service.getTaskById(any())).thenThrow(TaskNotFoundException.class);

        Assertions.assertThrows(TaskNotFoundException.class, () -> controller.getTaskById(1L));
    }

    @Test
    public void testGetTaskByNameTaskNotFoundException() throws TaskNotFoundException {
        when(service.getTaskByName(any())).thenThrow(TaskNotFoundException.class);

        Assertions.assertThrows(TaskNotFoundException.class, () -> controller.getTaskByName("Name"));
    }
}