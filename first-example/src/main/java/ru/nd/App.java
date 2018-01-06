package ru.nd;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.nd.beans.Client;
import ru.nd.beans.Event;
import ru.nd.beans.EventType;
import ru.nd.loggers.EventLogger;

import java.util.Map;

public class App {
    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType, EventLogger> loggers;

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultLogger = eventLogger;
        this.loggers = loggers;
    }

    public App() {}

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

        App app = (App)ctx.getBean("app");

        app.logEvents(ctx);

        ctx.close();
    }

    public void logEvents(ApplicationContext ctx) {
        Event event = ctx.getBean(Event.class);
        logEvent(EventType.INFO, event, "Some event for 1");

        event = ctx.getBean(Event.class);
        logEvent(EventType.INFO, event, "One more event for 1");

        event = ctx.getBean(Event.class);
        logEvent(EventType.INFO, event, "And one more event for 1");

        event = ctx.getBean(Event.class);
        logEvent(EventType.ERROR, event, "Some event for 2");

        event = ctx.getBean(Event.class);
        logEvent(null, event, "Some event for 3");
    }

    private void logEvent(EventType eventType, Event event, String msg) {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);

        EventLogger logger = loggers.get(eventType);
        if (logger == null) {
            logger = defaultLogger;
        }

        logger.logEvent(event);
    }

    public EventLogger getDefaultLogger() {
        return defaultLogger;
    }
}
