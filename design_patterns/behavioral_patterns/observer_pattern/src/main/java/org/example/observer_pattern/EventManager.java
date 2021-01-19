package org.example.observer_pattern;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class EventManager {
    HashMap<String, List<EventListener>> eventListeners = new HashMap<String, List<EventListener>>();

    public void subscrible(String eventType, EventListener eventListener) {
        if (!eventListeners.containsKey(eventType)) {
            eventListeners.put(eventType, new ArrayList<EventListener>());
        }
        eventListeners.get(eventType).add(eventListener);
    }

    public void unsubscrible(String eventType, EventListener eventListener) {
        if (eventListeners.containsKey(eventType)) {
            eventListeners.get(eventType).remove(eventListener);
        }
    }

    public void notify(String eventType, Object context) {
        if (eventListeners.containsKey(eventType)) {
            List<EventListener> listeners = eventListeners.get(eventType);
            for (EventListener listener : listeners) {
                listener.update(context);
            }
        }
    }
}
