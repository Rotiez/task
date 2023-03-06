package com.vtb.task.validation.aspect;

import com.vtb.task.entity.AuditLog;
import com.vtb.task.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Around("@annotation(com.vtb.task.validation.interfaces.Audit)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Object response = null;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String httpMethod = "";
        String status = "";
        String errorMessage = "";

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
            if (ex instanceof ResponseStatusException) {
                ResponseStatusException responseStatusException = (ResponseStatusException) ex;
                errorMessage = responseStatusException.getReason();
            } else {
                errorMessage = ex.getMessage();
            }
            throw ex;
        } finally {
            // Save log to database
            AuditLog log = new AuditLog();
            log.setMethodName(methodName);
            log.setClassName(className);
            log.setHttpMethod(httpMethod);
            log.setStatus(status);
            log.setErrorMessage(errorMessage);
            log.setTimestamp(new Date());
            auditLogRepository.save(log);
        }

        return response;
    }
}
