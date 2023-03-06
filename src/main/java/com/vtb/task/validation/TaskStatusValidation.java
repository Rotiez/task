package com.vtb.task.validation;

import com.vtb.task.validation.interfaces.ValidateTaskStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class TaskStatusValidation implements ConstraintValidator<ValidateTaskStatus, String> {
    @Override
    public boolean isValid(String taskStatus, ConstraintValidatorContext context) {
        List<String> taskStatuses= Arrays.asList("In process", "Waiting", "Closed");
        return taskStatuses.contains(taskStatus);
    }
}
