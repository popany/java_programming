package org.example.visitor_pattern;

public class Fuji implements Fruit {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }   
}
