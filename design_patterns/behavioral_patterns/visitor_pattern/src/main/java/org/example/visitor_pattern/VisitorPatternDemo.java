// https://refactoring.guru/design-patterns/visitor

package org.example.visitor_pattern;

import org.example.visitor_pattern.Citrus;
import org.example.visitor_pattern.FruitVisitor;
import org.example.visitor_pattern.GrapeFruit;
import org.example.visitor_pattern.Lemon;
import org.example.visitor_pattern.Orange;
import org.example.visitor_pattern.PopularFruit;

public class VisitorPatternDemo {
    public static void main(String[] args) {
        Apple apple = new Apple();
        apple.add(new Fuji());
        apple.add(new Gala());

        Citrus citrus = new Citrus();
        citrus.add(new Orange());
        citrus.add(new Lemon());
        citrus.add(new GrapeFruit());

        PopularFruit popularFruit = new PopularFruit();
        popularFruit.add(apple);
        popularFruit.add(citrus);

        FruitVisitor fruitVisitor = new FruitVisitor();
        popularFruit.accept(fruitVisitor);
    }
}
