package com.vtb.task.controller;

import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.exception.UnknownException;
import com.vtb.task.service.TaskServiceSave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TaskControllerSaveTest {

    @InjectMocks
    private TaskControllerSave controller;

    @Mock
    private TaskServiceSave service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveTaskSuccess() throws UnknownException {
        TaskRequest request = TaskRequest.builder()
                .name("Test Task")
                .type("Type A")
                .status("New")
                .owner("John Doe")
                .executor("Jane Doe")
                .build();
        TaskResponse response = TaskResponse.builder()
                .name("Test Task")
                .type("Type A")
                .status("New")
                .owner("John Doe")
                .executor("Jane Doe")
                .build();

        when(service.saveTask(any(TaskRequest.class))).thenReturn(response);

        ResponseEntity<TaskResponse> result = controller.saveTask(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
}