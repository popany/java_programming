package org.example.visitor_pattern;

public class GrapeFruit implements Fruit {
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }   
}
