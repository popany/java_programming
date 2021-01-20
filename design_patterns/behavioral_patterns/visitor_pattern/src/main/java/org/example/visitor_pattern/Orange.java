package org.example.visitor_pattern;

public class Orange implements Fruit {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }   
}
