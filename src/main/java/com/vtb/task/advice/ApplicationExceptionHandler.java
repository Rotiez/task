package com.vtb.task.advice;

import com.vtb.task.exception.DefaultException;
import com.vtb.task.exception.TaskNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        StackTraceElement[] st = new Throwable().getStackTrace();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
            //errorMap.put("Status", String.valueOf(HttpStatus.BAD_REQUEST));
            //errorMap.put("StackTrace", Arrays.toString(st));          // StackTrace
        });
        return (errorMap);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFoundException.class)
    public Map<String, String> handleNotFoundException(TaskNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("status", String.valueOf(HttpStatus.NOT_FOUND));
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DefaultException.class)
    public Map<String, String> handleDefaultException(DefaultException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return  errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        StackTraceElement[] st = new Throwable().getStackTrace();
        ex.getConstraintViolations().stream().toList().forEach( constraintViolation -> {
            errorMap.put("Message", constraintViolation.getMessage());
            //errorMap.put("Status", String.valueOf(HttpStatus.BAD_REQUEST));
            //errorMap.put("StackTrace", Arrays.toString(st));
        });

        return errorMap;
    }
}
