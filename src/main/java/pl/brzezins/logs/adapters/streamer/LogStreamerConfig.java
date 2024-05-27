package pl.brzezins.logs.adapters.streamer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.brzezins.logs.domain.ports.LogStreamer;

@Configuration
class LogStreamerConfig {
    @Bean
    public LogStreamer logStreamer() {
        return new LogStreamerService();
    }
}
