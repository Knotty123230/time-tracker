package com.privat.timetracker.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {
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

    @AfterThrowing(pointcut = "controllerMethods() || serviceMethods() || repositoryMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Method {} threw exception: {}",
                joinPoint.getSignature().toShortString(), ex.getMessage(), ex);
    }
}