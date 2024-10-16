package com.sparta.springtrello.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class NotifyAspect {

    @Pointcut("@annotation(com.sparta.springtrello.annotation.NotifyEvent)")
    private void notifyAnnotation() {}

    @After("notifyAnnotation()")
    public Object adviceAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            return result;
        } finally {

        }
    }
}
