package org.example;

public class Foo {
    int id;
    String info;

    public void setId(int id) {
        this.id = id;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
    public Foo() {}

    public Foo(int id, String info) {
        this.id = id;
        this.info = info;
    }

    public String toString() {
        return String.format("id: %d, info: %s", id, info);   
    }
}
