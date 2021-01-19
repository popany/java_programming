// https://refactoring.guru/design-patterns/observer

package org.example.observer_pattern;

class ObserverPatternDemo {
    public static void main(String[] args) {
        EventManager eventManager = new EventManager();
        FooListerner fooListerner = new FooListerner();
        BarListerner barListerner = new BarListerner();

        eventManager.subscrible("A", fooListerner);
        eventManager.subscrible("A", barListerner);
        eventManager.subscrible("B", barListerner);

        eventManager.notify("A", "hello");
        eventManager.notify("B", 42);

        eventManager.unsubscrible("A", barListerner);

        eventManager.notify("A", "hello");
        eventManager.notify("B", 42);
    }
}