package com.example.AttendanceAndPayroll.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegisterMethod {
    Logger logger = LoggerFactory.getLogger(RegisterMethod.class);
    @Before("@annotation(com.example.AttendanceAndPayroll.aspect.LogMethod)")
    public void logMethod(JoinPoint joinPoint) {
        logger.info("Method "+ joinPoint.getSignature().getName()+" has been called." );
    }
}