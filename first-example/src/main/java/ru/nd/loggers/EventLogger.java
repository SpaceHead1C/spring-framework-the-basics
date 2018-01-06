package ru.nd.loggers;

import ru.nd.beans.Event;

public interface EventLogger {
    void logEvent(Event event);
}
