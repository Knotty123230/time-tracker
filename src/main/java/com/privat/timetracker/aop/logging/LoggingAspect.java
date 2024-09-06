package com.privat.timetracker.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.privat.timetracker.controller..*(..))")
    public void controllerMethods() {
    }

    @Pointcut("execution(* com.privat.timetracker.service..*(..))")
    public void serviceMethods() {
    }

    @Pointcut("execution(* com.privat.timetracker.repository..*(..))")
    public void repositoryMethods() {
    }

    @Before("controllerMethods() || serviceMethods() || repositoryMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "controllerMethods() || serviceMethods() || repositoryMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method {} returned with value: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    @After("controllerMethods() || serviceMethods() || repositoryMethods()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature().toShortString());
    }
}