package pl.brzezins.logs.adapters.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import pl.brzezins.logs.domain.ports.LogStreamer;
import pl.brzezins.logs.domain.ports.PointcutArgumentExtractor;

@Configuration
@EnableAspectJAutoProxy
class LogMonitoringConfig {
    @Bean
    public PointcutArgumentExtractor pointcutArgumentExtractor() {
        return new LogMonitoringArgumentExtractor();
    }

    @Bean
    public LogMonitoringAspect logMonitoringAspect(PointcutArgumentExtractor argumentExtractor, LogStreamer logStreamer, ObjectMapper objectMapper) {
        return new LogMonitoringAspect(argumentExtractor, logStreamer, objectMapper);
    }
}
