package com.nirmalks.bookstore.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.nirmalks.bookstore.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        logger.info("Entering method: {}.{}", className, methodName);
        Object result;
        try {
            result = joinPoint.proceed();
            logger.info("Exiting method: {}.{} with result: {}", className, methodName, result);
        } catch (Exception e) {
            logger.error("Exception in method: {}.{} - {}", className, methodName, e.getMessage());
            throw e;
        }
        return result;
    }
}
