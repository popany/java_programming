package org.example.observer_pattern;

public class BarListerner implements EventListener {
    public void update(Object context) {
        System.out.println("Bar: " + context);
    }
}
