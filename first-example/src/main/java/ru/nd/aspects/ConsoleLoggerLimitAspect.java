package ru.nd.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import ru.nd.beans.Event;
import ru.nd.loggers.EventLogger;

@Aspect
public class ConsoleLoggerLimitAspect {
    private final int maxCount;
    private final EventLogger otherLogger;

    private int currentCount = 0;

    public ConsoleLoggerLimitAspect(int maxCount, EventLogger otherLogger) {
        this.maxCount = maxCount;
        this.otherLogger = otherLogger;
    }

    @Pointcut("execution(* *.logEvent(..))")
    public void consoleLoggerMethods() {}

    @Around("consoleLoggerMethods() && within(ru.nd.loggers.ConsoleEventLogger) && args(event)")
    public void aroundLogEvent(ProceedingJoinPoint pjp, Event event) throws Throwable {
        if (currentCount < maxCount) {
            System.out.println("ConsoleEventLogger max count is not reached. Logging to " + otherLogger.getName());
            currentCount++;
            pjp.proceed(new Object[] {event});
        } else {
            System.out.println("ConsoleEventLogger max count is reached. Logging to " + otherLogger.getName());
            otherLogger.logEvent(event);
        }
    }
}
