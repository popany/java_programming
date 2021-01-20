package org.example.visitor_pattern;

import java.util.List;
import java.util.ArrayList;
import org.example.visitor_pattern.Visitor;

public class Apple implements Fruit {
    List<Fruit> fruits = new ArrayList<Fruit>();

    public void add(Fruit fruit) {
        fruits.add(fruit);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (Fruit fruit : fruits) {
            fruit.accept(visitor);
        }
    }
}
