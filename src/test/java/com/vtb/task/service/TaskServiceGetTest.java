package com.vtb.task.service;

import com.vtb.task.entity.Task;
import com.vtb.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class TaskServiceGetTest {

    @Mock
    private TaskRepository repository;

    @InjectMocks
    private TaskServiceGet service;

    @Test
    public void testGetAllTasks() {
        // given
        List<Task> goodTasks = new ArrayList<>();
        Task task1 = Task.builder().taskId((long)1).name("Task 1").type("Type 1").status("Status 1").owner("Owner 1").executor("Executor 1").build();
        Task task2 = Task.builder().taskId((long)2).name("Task 2").type("Type 2").status("Status 2").owner("Owner 2").executor("Executor 2").build();
        goodTasks.add(task1);
        goodTasks.add(task2);

        when(repository.findAll()).thenReturn(goodTasks);

        // when
        List<Task> result = service.getAllTasks();

        // then
        assertEquals(goodTasks.size(), result.size());
        assertEquals(goodTasks.get(0).getName(), result.get(0).getName());
        assertEquals(goodTasks.get(1).getName(), result.get(1).getName());
        assertEquals(goodTasks.get(0).getType(), result.get(0).getType());
        assertEquals(goodTasks.get(1).getType(), result.get(1).getType());
        assertEquals(goodTasks.get(0).getStatus(), result.get(0).getStatus());
        assertEquals(goodTasks.get(1).getStatus(), result.get(1).getStatus());
        assertEquals(goodTasks.get(0).getOwner(), result.get(0).getOwner());
        assertEquals(goodTasks.get(1).getOwner(), result.get(1).getOwner());
        assertEquals(goodTasks.get(0).getExecutor(), result.get(0).getExecutor());
        assertEquals(goodTasks.get(1).getExecutor(), result.get(1).getExecutor());
    }
}