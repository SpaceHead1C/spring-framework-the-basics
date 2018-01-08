package ru.nd.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class LoggingAspect {
    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods() {}

    @Pointcut("allLogEventMethods() && within(*.*File*Logger)")
    private void logEventInsideFileLogers() {}

    @Before("allLogEventMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("BEFORE : " + joinPoint.getTarget().getClass().getSimpleName()
                + " " + joinPoint.getSignature().getName());
    }

// Параметром advice-аннотации может быть определение pointcut (помимо имени),
// но нельзя будет переиспользовать pointcut
//    @Before("execution(* *.logEvent(..))")
//    public void logBefore(JoinPoint joinPoint) {}

    @AfterReturning(pointcut = "allLogEventMethods()", returning = "retVal")
    public void logAfter(Object retVal) {
        System.out.println("Returned value: " + retVal);
    }

    @AfterThrowing(pointcut = "allLogEventMethods()", throwing = "ex")
    public void logAfterThrow(Throwable ex) {
        System.out.println("Throw: " + ex);
    }
}
