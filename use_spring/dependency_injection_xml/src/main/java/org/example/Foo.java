package org.example;

public class Foo {
    int id;
    String info;

    public Foo(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public String toString() {
        return String.format("id: %d, info: %s", id, info);   
    }
}
