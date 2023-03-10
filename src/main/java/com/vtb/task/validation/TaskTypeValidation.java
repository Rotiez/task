package com.vtb.task.validation;

import com.vtb.task.validation.interfaces.ValidateTaskType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class TaskTypeValidation implements ConstraintValidator<ValidateTaskType, String> {

    //Валидация типа задачи на соответствие типу из списка
    @Override
    public boolean isValid(String taskType, ConstraintValidatorContext context) {
        List<String> taskTypes = Arrays.asList("Developing", "Analytics");
        return taskTypes.contains(taskType);
    }
}
