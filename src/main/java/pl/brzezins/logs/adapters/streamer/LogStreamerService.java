package pl.brzezins.logs.adapters.streamer;

import pl.brzezins.logs.domain.model.LogMessage;
import pl.brzezins.logs.domain.ports.LogStreamer;

class LogStreamerService implements LogStreamer {
    @Override
    public void send(LogMessage message) {
        throw new UnsupportedOperationException("Method not implemented!");
    }
}
