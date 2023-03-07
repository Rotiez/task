package com.vtb.task.advice;

import com.vtb.task.exception.ErrorMessage;
import com.vtb.task.exception.UnknownException;
import com.vtb.task.exception.TaskNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    private final boolean debug = Boolean.parseBoolean(System.getenv("DEBUG_MODE"));

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFoundException.class)
    public Map<String, String> handleNotFoundException(TaskNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        if (debug){
            errorMap.put("errorMessage", ex.getMessage());
            errorMap.put("status", String.valueOf(HttpStatus.NOT_FOUND));
            errorMap.put("StackTrace", Arrays.toString(ex.getStackTrace()));
        } else{
            errorMap.put("errorMessage", ex.getMessage());
            errorMap.put("status", String.valueOf(HttpStatus.NOT_FOUND));
        }
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnknownException.class)
    public Map<String, String> handleDefaultException(UnknownException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return  errorMap;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ErrorMessage> handleConstraintViolationException(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(violation -> new ErrorMessage(
                        violation.getPropertyPath().toString(),
                        violation.getMessage() + " (Вы указали: " + violation.getInvalidValue().toString() + ")"
                )).collect(Collectors.toList());
    }

/*
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
            errorMap.put("Status", String.valueOf(HttpStatus.BAD_REQUEST));
        });
        return (errorMap);
    }
    */

}
