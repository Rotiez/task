package com.vtb.task.aspect;

import com.vtb.task.entity.AuditLog;
import com.vtb.task.exception.ErrorMessage;
import com.vtb.task.exception.UnknownException;
import com.vtb.task.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Around("@annotation(com.vtb.task.aspect.Audit)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Object response = null;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String httpMethod = null;
        String status = "";
        Map<String, String> errorMap = new HashMap<>();

        //Получение HTTP Метода из запроса
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            httpMethod = request.getMethod();
        }

        //Отлов исключений и получение errorMessage
        try {
            response = joinPoint.proceed();
            status = "Success";
        } catch (Exception ex) {
            status = "Error";

            if (ex instanceof ResponseStatusException responseStatusException) {
                errorMap.put("Message", responseStatusException.getReason());
            }

            else if(ex instanceof ConstraintViolationException) {
                List<ErrorMessage> errorMessageList = ((ConstraintViolationException) ex).getConstraintViolations().stream().map(exception ->
                        new ErrorMessage("Message", exception.getMessage())).toList();
                for(int i = 0; i < errorMessageList.size(); i++) {
                    errorMap.put(errorMessageList.get(i).getError() + "_" + String.valueOf(i), errorMessageList.get(i).getDescription());
                }
            }

            else if(ex instanceof UnknownException unknownException){
                errorMap.put("Message", unknownException.getMessage());
            }

            else {
                errorMap.put("Message", ex.getMessage());
            }
            throw ex;
        } finally {
            //Сохранение
            AuditLog log = new AuditLog();
            log.setMethodName(methodName);
            log.setClassName(className);
            log.setHttpMethod(httpMethod);
            log.setStatus(status);
            log.setErrorMessage(errorMap.toString());
            log.setTimestamp(new Date());
            auditLogRepository.save(log);
        }

        return response;
    }
}
