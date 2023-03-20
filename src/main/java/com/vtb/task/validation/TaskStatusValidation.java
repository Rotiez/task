package com.vtb.task.validation;

import com.vtb.task.validation.interfaces.ValidateTaskStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

/**
 * Класс с методом {@link #isValid(String, ConstraintValidatorContext)} для валидации статуса задачи ({@link com.vtb.task.entity.Task})
 */
public class TaskStatusValidation implements ConstraintValidator<ValidateTaskStatus, String> {

    /**
     * Метод для валидации статусса задачи на соответствие списку
     * @param taskStatus object to validate
     * @param context context in which the constraint is evaluated
     * @return boolean
     */
    @Override
    public boolean isValid(String taskStatus, ConstraintValidatorContext context) {
        List<String> taskStatuses= Arrays.asList("In process", "Waiting", "Closed");
        return taskStatuses.contains(taskStatus);
    }
}
