package org.example.visitor_pattern;

public class Gala implements Fruit {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }   
}
