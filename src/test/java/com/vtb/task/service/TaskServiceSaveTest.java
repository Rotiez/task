package com.vtb.task.service;

import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.entity.Task;
import com.vtb.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskServiceSaveTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceSave taskServiceSave;

    @Test
    public void saveTaskTest() {
        // given
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("test task");
        taskRequest.setType("test type");
        taskRequest.setStatus("test status");
        taskRequest.setOwner("test owner");
        taskRequest.setExecutor("test executor");

        Task savedTask = Task.builder()
                .taskId((long)1)
                .name("test task")
                .type("test type")
                .status("test status")
                .owner("test owner")
                .executor("test executor")
                .build();

        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(savedTask);

        // when
        TaskResponse savedTaskResponse = taskServiceSave.saveTask(taskRequest);

        // then
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());

        Task capturedTask = taskCaptor.getValue();
        assertThat(capturedTask.getName()).isEqualTo("test task");
        assertThat(capturedTask.getType()).isEqualTo("test type");
        assertThat(capturedTask.getStatus()).isEqualTo("test status");
        assertThat(capturedTask.getOwner()).isEqualTo("test owner");
        assertThat(capturedTask.getExecutor()).isEqualTo("test executor");


        assertThat(savedTaskResponse.getName()).isEqualTo("test task");
        assertThat(savedTaskResponse.getType()).isEqualTo("test type");
        assertThat(savedTaskResponse.getStatus()).isEqualTo("test status");
        assertThat(savedTaskResponse.getOwner()).isEqualTo("test owner");
        assertThat(savedTaskResponse.getExecutor()).isEqualTo("test executor");
    }
}