package org.example;

public class Foo {
    Bar bar;

    public Foo(Bar bar) {
        this.bar = bar;
    }

    public String toString() {
        return String.format("bar: %s", bar.getInfo());
    }
}
