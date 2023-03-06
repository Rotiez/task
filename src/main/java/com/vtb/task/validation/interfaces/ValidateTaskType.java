package com.vtb.task.validation.interfaces;

import com.vtb.task.validation.TaskTypeValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.util.Arrays;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TaskTypeValidation.class)
public @interface ValidateTaskType {

    public String message() default "Invalid taskType";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
