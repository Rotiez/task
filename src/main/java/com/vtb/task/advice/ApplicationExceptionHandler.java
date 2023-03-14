package com.vtb.task.advice;

import com.vtb.task.exception.UnknownException;
import com.vtb.task.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

/**
 * Обработчик исключений
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @Value("${debug-mode}")
    private boolean debug;

    /**
     * Метод для добавления в {@link Map}<{@link String}, {@link String}>
     * StackTrace в случае переменной среды DEBUG_MODE=true
     * @param errorMap {@link Map}<{@link String}, {@link String}>
     * @param debug boolean переменная
     * @param ex exception
     * @return возвращает {@link Map}<{@link String}, {@link String}>
     */
    private Map<String, String> mapAddStackTrace(Map<String, String> errorMap, boolean debug, Exception ex){
        if (debug){
            errorMap.put("StackTrace", Arrays.toString(ex.getStackTrace()));
        }
        return errorMap;
    }

    /**
     * Метод для обработки исключения {@link TaskNotFoundException}
     * @param ex exception
     * @return возвращает результат работы метода {@link #mapAddStackTrace(Map, boolean, Exception)}
     * ({@link Map}<{@link String}, {@link String}>)
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TaskNotFoundException.class)
    public Map<String, String> handleNotFoundException(TaskNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        errorMap.put("status", String.valueOf(HttpStatus.NOT_FOUND));
        return mapAddStackTrace(errorMap, debug, ex);
    }

    /**
     * Метод для обработки исключения {@link UnknownException}
     * @param ex exception
     * @return возвращает результат работы метода {@link #mapAddStackTrace(Map, boolean, Exception)}
     * ({@link Map}<{@link String}, {@link String}>)
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(UnknownException.class)
    public Map<String, String> handleDefaultException(UnknownException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", ex.getMessage());
        return  mapAddStackTrace(errorMap, debug, ex);
    }

    /**
     * Метод для обработки исключения {@link MethodArgumentNotValidException}
     * @param ex exception
     * @return возвращает результат работы метода {@link #mapAddStackTrace(Map, boolean, Exception)}
     * ({@link Map}<{@link String}, {@link String}>)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        errorMap.put("Status", String.valueOf(HttpStatus.BAD_REQUEST));
        return mapAddStackTrace(errorMap, debug, ex);
    }

/*    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ErrorMessage> handleConstraintViolationException(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(violation -> new ErrorMessage(
                        violation.getPropertyPath().toString(),
                        violation.getMessage() + " (Вы указали: " + violation.getInvalidValue().toString() + ")"
                )).collect(Collectors.toList());
    }*/
}
