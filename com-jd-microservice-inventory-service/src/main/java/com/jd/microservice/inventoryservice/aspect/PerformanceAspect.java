package com.jd.microservice.inventoryservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    private static final Logger perfLog = LoggerFactory.getLogger("com.jd.performance");

    @Around("execution(* com.jd.microservice.inventoryservice.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        String methodName = joinPoint.getSignature().toShortString();
        perfLog.info("Method: {} | Execution Time: {}ms", methodName, executionTime);

        return proceed;
    }
}
