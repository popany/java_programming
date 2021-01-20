package org.example.visitor_pattern;

public class Lemon implements Fruit {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }   
}
