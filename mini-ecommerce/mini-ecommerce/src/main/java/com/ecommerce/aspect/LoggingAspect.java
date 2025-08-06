package com.ecommerce.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    // Log before service method execution
    @Before("execution(* com.ecommerce.service.*.*(..))")
    public void logBefore(JoinPoint jp) {
        System.out.println("➡ Entering: " + jp.getSignature().toShortString());
    }

    // Log after service method execution
    @After("execution(* com.ecommerce.service.*.*(..))")
    public void logAfter(JoinPoint jp) {
        System.out.println("⬅ Exiting: " + jp.getSignature().toShortString());
    }
}
