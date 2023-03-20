package com.vtb.task.validation;

import com.vtb.task.validation.interfaces.ValidateTaskType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

/**
 * Класс с методос {@link #isValid(String, ConstraintValidatorContext)} для валидации типа задачи
 */
public class TaskTypeValidation implements ConstraintValidator<ValidateTaskType, String> {

    /**
     * Метод для валидации типа задачи на соответствие списку
     * @param taskType object to validate
     * @param context context in which the constraint is evaluated
     * @return boolean
     */
    @Override
    public boolean isValid(String taskType, ConstraintValidatorContext context) {
        List<String> taskTypes = Arrays.asList("Developing", "Analytics");
        return taskTypes.contains(taskType);
    }
}
