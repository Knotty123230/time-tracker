package com.privat.timetracker.aop.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging method execution details in specified packages.
 * This aspect logs information about method entry, return values, and method exit
 * for methods in the controller, service, and repository layers.
 */
@Aspect
@Component
public class LoggingAspect {
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
     * Advice that logs details before the execution of methods matched by the specified
     * pointcuts (controller, service, and repository methods).
     *
     * @param joinPoint The join point representing the method execution.
     */
    @Before("controllerMethods() || serviceMethods() || repositoryMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    /**
     * Advice that logs details after the successful execution of methods matched by the specified
     * pointcuts (controller, service, and repository methods).
     *
     * @param joinPoint The join point representing the method execution.
     * @param result    The result returned by the method.
     */
    @AfterReturning(pointcut = "controllerMethods() || serviceMethods() || repositoryMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Method {} returned with value: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    /**
     * Advice that logs details after the execution of methods matched by the specified
     * pointcuts (controller, service, and repository methods), regardless of the outcome.
     *
     * @param joinPoint The join point representing the method execution.
     */
    @After("controllerMethods() || serviceMethods() || repositoryMethods()")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature().toShortString());
    }
}