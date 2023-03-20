package com.vtb.task.exception;

public class TaskNotFoundException extends Exception{
    /**
     * Исключение не неайденной задачи
     * @param message сообщение
     */
    public TaskNotFoundException(String message){
        super(message);
    }
}
