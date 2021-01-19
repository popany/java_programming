package org.example.observer_pattern;

public class FooListerner implements EventListener {
    public void update(Object context) {
        System.out.println("Foo: " + context);
    }
}
