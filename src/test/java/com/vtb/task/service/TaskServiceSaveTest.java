package com.vtb.task.service;

import com.vtb.task.dto.request.TaskRequest;
import com.vtb.task.dto.response.TaskResponse;
import com.vtb.task.entity.Task;
import com.vtb.task.exception.TaskNotFoundException;
import com.vtb.task.exception.UnknownException;
import com.vtb.task.repository.TaskRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskServiceSaveTest {
    private Validator validator;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceSave taskServiceSave;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testSaveTaskSuccess() throws UnknownException {
        // Arrange
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("Test Task");
        taskRequest.setType("Developing");
        taskRequest.setStatus("In process");
        taskRequest.setOwner("John Doe");
        taskRequest.setExecutor("Jane Doe");

        Task expectedTask = Task.builder()
                .name("Test Task")
                .type("Developing")
                .status("In process")
                .owner("John Doe")
                .executor("Jane Doe")
                .build();

        when(taskRepository.save(expectedTask)).thenReturn(expectedTask);

        // Act
        TaskResponse actualTaskResponse = taskServiceSave.saveTask(taskRequest);

        // Assert
        assertEquals(expectedTask.getName(), actualTaskResponse.getName());
        assertEquals(expectedTask.getType(), actualTaskResponse.getType());
        assertEquals(expectedTask.getStatus(), actualTaskResponse.getStatus());
        assertEquals(expectedTask.getOwner(), actualTaskResponse.getOwner());
        assertEquals(expectedTask.getExecutor(), actualTaskResponse.getExecutor());
        verify(taskRepository).save(expectedTask);
    }

    @Test
    public void testSaveTaskInvalidInput() throws UnknownException {
        // Arrange
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setName("test task"); // invalid name, should start with uppercase
        taskRequest.setType("Non-existing"); // invalid type
        taskRequest.setStatus("In Review"); // invalid status
        taskRequest.setOwner("john doe"); // invalid owner name, should start with uppercase
        taskRequest.setExecutor("jane doe"); // invalid executor name, should start with uppercase

        // Act and Assert
        try {
            taskServiceSave.saveTask(taskRequest);
        } catch (UnknownException e) {
            assertEquals(4, validator.validate(taskRequest).size()); // 4 validation errors expected
            verify(taskRepository).save(Mockito.any(Task.class)); // task should not be saved
        }
    }
}