package ru.nd.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Aspect
public class StatisticsAspect {
    private Map<Class<?>, Integer> counter = new HashMap<Class<?>, Integer>();

    @Pointcut("execution(* *.logEvent(..))")
    private void allLogEventMethods() {}

    @AfterReturning("allLogEventMethods()")
    public void count(JoinPoint jp) {
        Class<?> c = jp.getTarget().getClass();
        counter.merge(c, 1, Integer::sum); // like a:
//        if (!counter.containsKey(c)) {
//            counter.put(c, 0);
//        }
//        counter.put(c, counter.get(c) + 1);
    }

    public Map<Class<?>, Integer> getCounter() {
        return Collections.unmodifiableMap(counter);
    }

    @AfterReturning("execution(* ru.nd.App.logEvents(..))")
    public void outputLoggingCounter() {
        System.out.println("Loggers statistics. Number of calls: ");
        for (Entry<Class<?>, Integer> entry : counter.entrySet()) {
            System.out.println("    " + entry.getKey().getSimpleName() + ": " + entry.getValue());
        }
    }
}
