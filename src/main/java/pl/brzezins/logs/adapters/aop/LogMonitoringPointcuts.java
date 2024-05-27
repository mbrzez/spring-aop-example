package pl.brzezins.logs.adapters.aop;

import org.aspectj.lang.annotation.Pointcut;

class LogMonitoringPointcuts {
    @Pointcut("@annotation(pl.brzezins.logs.domain.ports.LogRequestMonitoring)")
    static void logRequestMonitoring() {};

    @Pointcut("execution(@(@pl.brzezins.logs.domain.ports.LogRequestMonitoring *) * *(..))")
    static void  logRequestMonitoringInherited() {};

    @Pointcut("@annotation(pl.brzezins.logs.domain.ports.LogResponseMonitoring)")
    static void  logResponseMonitoring() {};

    @Pointcut("execution(@(@pl.brzezins.logs.domain.ports.LogResponseMonitoring *) * *(..))")
    static void  logResponseMonitoringInherited() {};
}
