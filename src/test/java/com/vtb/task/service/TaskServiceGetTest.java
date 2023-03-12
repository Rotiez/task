package com.vtb.task.service;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceGetTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceGet service;


    @Test
    void testGetAllTasks() throws TaskNotFoundException {
        List<Task> tasks = new ArrayList<>();

        Task task1 = new Task();
        task1.setTaskId(1L);
        task1.setName("Task 1");
        task1.setType("Developing");
        task1.setStatus("In process");
        task1.setOwner("Owner 1");
        task1.setExecutor("Executor 1");

        Task task2 = new Task();
        task2.setTaskId(2L);
        task2.setName("Task 2");
        task2.setType("Analytics");
        task2.setStatus("Waiting");
        task2.setOwner("Owner 2");
        task2.setExecutor("Executor 2");

        tasks.add(task1);
        tasks.add(task2);

        when(repository.findAll()).thenReturn(tasks);

        List<Task> result = service.getAllTasks();

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllTasksEmptyList() {
        assertThrows(TaskNotFoundException.class, () -> service.getAllTasks());
    }

    @Test
    void testGetTaskById() throws TaskNotFoundException {

        Task task = new Task();
        task.setTaskId(1L);
        task.setName("Task 1");
        task.setType("Developing");
        task.setStatus("In process");
        task.setOwner("Owner 1");
        task.setExecutor("Executor 1");

        when(repository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = service.getTaskById(1L);

        assertEquals("Task 1", result.get().getName());
    }

    @Test
    void testGetTaskByIdEmptyList() {
        Long id = 999L;
        assertThrows(TaskNotFoundException.class, () -> service.getTaskById(id));
    }


    @Test
    void testGetTaskByName() throws TaskNotFoundException {

        List<Task> task = new ArrayList<>();
        Task task1 = new Task();
        task1.setTaskId(1L);
        task1.setName("Task 1");
        task1.setType("Developing");
        task1.setStatus("In process");
        task1.setOwner("Owner 1");
        task1.setExecutor("Executor 1");

        Task task2 = new Task();
        task2.setTaskId(2L);
        task2.setName("Task 2");
        task2.setType("Analytics");
        task2.setStatus("Waiting");
        task2.setOwner("Owner 2");
        task2.setExecutor("Executor 2");

        task.add(task1);
        task.add(task2);

        when(repository.findByName("Task 1")).thenReturn(task);

        List<Task> result = service.getTaskByName("Task 1");

        assertEquals(2, result.size());
    }

    @Test
    void testGetTaskByNameEmptyList() {
        assertThrows(TaskNotFoundException.class, () -> service.getTaskByName("First"));
    }
}