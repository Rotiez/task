package com.vtb.task.aspect;

import com.vtb.task.entity.AuditLog;
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        String httpMethod = "";
        String status = "";
        Map<String, String> errorMap = new HashMap<>();

        // Get HTTP Method
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            httpMethod = request.getMethod();
        }

        // Call method and get response
        try {
            response = joinPoint.proceed();
            status = "Success";
        } catch (Exception ex) {
            status = "Error";

            if (ex instanceof ResponseStatusException responseStatusException) {
                errorMap.put("Message", responseStatusException.getReason());
            }

            else if(ex instanceof ConstraintViolationException) {
                ((ConstraintViolationException) ex).getConstraintViolations().stream().toList().forEach( constraintViolation -> {
                    errorMap.put("Message", constraintViolation.getMessage());
                });
            }

            else if(ex instanceof UnknownException unknownException){
                errorMap.put("Message", unknownException.getMessage());
            }

            else {
                errorMap.put("Message", ex.getMessage());
            }
            throw ex;
        } finally {
            // Save log to database
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
