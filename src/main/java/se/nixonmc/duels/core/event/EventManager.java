package se.nixonmc.duels.core.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class EventManager {

    private final Map<Class<?>, List<Consumer<?>>> listeners =
            new HashMap<>();

    public <T> void subscribe(
            Class<T> eventType,
            Consumer<T> listener
    ) {
        listeners
                .computeIfAbsent(eventType, ignored -> new ArrayList<>())
                .add(listener);
    }

    @SuppressWarnings("unchecked")
    public <T> void publish(T event) {
        List<Consumer<?>> eventListeners =
                listeners.get(event.getClass());

        if (eventListeners == null) {
            return;
        }

        for (Consumer<?> listener : eventListeners) {
            ((Consumer<T>) listener).accept(event);
        }
    }

    public <T> void unsubscribe(
            Class<T> eventType,
            Consumer<T> listener
    ) {
        List<Consumer<?>> eventListeners =
                listeners.get(eventType);

        if (eventListeners == null) {
            return;
        }

        eventListeners.remove(listener);

        if (eventListeners.isEmpty()) {
            listeners.remove(eventType);
        }
    }

    public void clear() {
        listeners.clear();
    }
}