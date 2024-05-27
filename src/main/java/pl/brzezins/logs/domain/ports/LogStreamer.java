package pl.brzezins.logs.domain.ports;

import pl.brzezins.logs.domain.model.LogMessage;

public interface LogStreamer {
    void send(LogMessage message);
}