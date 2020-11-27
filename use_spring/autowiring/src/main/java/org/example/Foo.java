package org.example;

public class Foo {
    int id;
    String info;
    Bar bar;

    public void setId(int id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setBar(Bar bar) {
        this.bar = bar;
    }
    
    public Foo() {}

    public Foo(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public String toString() {
        return String.format("id: %d, bar: %s, info: %s", id, bar.getName(), info);   
    }
}
