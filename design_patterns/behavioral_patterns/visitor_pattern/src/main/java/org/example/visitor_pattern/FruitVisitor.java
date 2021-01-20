package org.example.visitor_pattern;

import org.example.visitor_pattern.Visitor;

public class FruitVisitor implements Visitor {
    public void visit(PopularFruit fruit) {
        System.out.println("Popular Fruit");
    }

    public void visit(Apple fruit) {
        System.out.println("Apple");
    }

    public void visit(Citrus fruit) {
        System.out.println("Citrus");
    }

    public void visit(Fuji fruit) {
        System.out.println("Fuji");
    }

    public void visit(Gala fruit) {
        System.out.println("Gala");
    }

    public void visit(Orange fruit) {
        System.out.println("Orange");
    }

    public void visit(Lemon fruit) {
        System.out.println("Lemon");
    }

    public void visit(GrapeFruit fruit) {
        System.out.println("GrapeFruit");
    }
}
