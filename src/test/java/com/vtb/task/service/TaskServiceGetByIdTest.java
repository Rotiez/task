package com.vtb.task.service;

import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class TaskServiceGetByIdTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceGetById service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTaskSuccess() throws TaskNotFoundException {
        // Given
        Task task = Task.builder()
                .taskId((long)1)
                .name("Test Task")
                .type("Test Type")
                .status("Test Status")
                .owner("Test Owner")
                .executor("Test Executor")
                .build();

        when(repository.findByTaskId(anyLong())).thenReturn(task);

        // When
        Task result = service.getTask((long)1);

        // Then
        assertEquals(task, result);
    }

    @Test
    public void testGetTaskNotFound() {
        // Given
        when(repository.findByTaskId(anyLong())).thenReturn(null);

        // When
        Throwable exception = assertThrows(TaskNotFoundException.class, () -> {
            service.getTask((long)1);
        });

        // Then
        assertEquals("Task not found with id: 1", exception.getMessage());
    }
}