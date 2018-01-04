package ru.nd;

import ru.nd.beans.Client;
import ru.nd.loggers.ConsoleEventLogger;
import ru.nd.loggers.EventLogger;

public class App {
    private Client client;
    private EventLogger eventLogger;

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public App() {}

    public static void main(String[] args) {
        App app = new App(new Client("1", "John Smith"), new ConsoleEventLogger());

        app.logEvent("Some event for user 1");
    }

    private void logEvent(String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        eventLogger.logEvent(message);
    }
}
