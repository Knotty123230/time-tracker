package com.privat.timetracker.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging exceptions thrown by methods in specified packages.
 * This aspect captures exceptions thrown by methods in the controller, service,
 * and repository layers and logs the details of these exceptions.
 */
@Aspect
@Component
public class ExceptionLoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Pointcut for all methods in the controller package.
     */
    @Pointcut("execution(* com.privat.timetracker.controller..*(..))")
    public void controllerMethods() {
    }

    /**
     * Pointcut for all methods in the service package.
     */
    @Pointcut("execution(* com.privat.timetracker.service..*(..))")
    public void serviceMethods() {
    }

    /**
     * Pointcut for all methods in the repository package.
     */
    @Pointcut("execution(* com.privat.timetracker.repository..*(..))")
    public void repositoryMethods() {
    }

    /**
     * Advice that logs details of exceptions thrown by methods matched by the
     * specified pointcuts (controller, service, and repository methods).
     *
     * @param joinPoint The join point representing the method execution where the exception occurred.
     * @param ex        The exception that was thrown.
     */
    @AfterThrowing(pointcut = "controllerMethods() || serviceMethods() || repositoryMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        logger.error("Method {} threw exception: {}",
                joinPoint.getSignature().toShortString(), ex.getMessage(), ex);
    }
}