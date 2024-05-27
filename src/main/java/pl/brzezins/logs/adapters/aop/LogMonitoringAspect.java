package pl.brzezins.logs.adapters.aop;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.web.bind.annotation.RequestBody;
import pl.brzezins.logs.domain.exception.PointcutArgumentException;
import pl.brzezins.logs.domain.model.LogMessage;
import pl.brzezins.logs.domain.ports.LogStreamer;
import pl.brzezins.logs.domain.ports.PointcutArgumentExtractor;

@Slf4j
@Aspect
@RequiredArgsConstructor
class LogMonitoringAspect {
    private final PointcutArgumentExtractor argumentExtractor;
    private final LogStreamer logStreamer;
    private final ObjectMapper objectMapper;

    @Before("LogMonitoringPointcuts.logRequestMonitoring() || LogMonitoringPointcuts.logRequestMonitoringInherited()")
    public void logMonitoringBefore(JoinPoint joinPoint) {
        try {
            var requestBody = argumentExtractor.extractAnnotatedArgument(joinPoint, RequestBody.class);
            var requestContent = objectMapper.writeValueAsString(requestBody);

            logStreamer.send(new LogMessage(requestContent));
        } catch (PointcutArgumentException | JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @AfterReturning(pointcut = "LogMonitoringPointcuts.logResponseMonitoring() || LogMonitoringPointcuts.logResponseMonitoringInherited()", returning = "returnValue")
    public void logMonitoringAfter(JoinPoint joinPoint, Object returnValue) {
        try {
            var responseContent = objectMapper.writeValueAsString(returnValue);

            logStreamer.send(new LogMessage(responseContent));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
