package com.vtb.task.validation.interfaces;

import com.vtb.task.validation.TaskStatusValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TaskStatusValidation.class)
public @interface ValidateTaskStatus {

    public String message() default "Invalid taskStatus";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
