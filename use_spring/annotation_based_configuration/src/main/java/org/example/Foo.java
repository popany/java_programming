package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Foo {

    @Qualifier("bar1")
    Bar bar1;
    Bar bar2;

    @Qualifier("baz1")
    Baz baz1;
    Baz baz2;

    @Autowired
    public Foo(@Qualifier("bar2") Bar bar, @Qualifier("baz2") Baz baz) {
        this.bar2 = bar;
        this.baz2 = baz;
    }

    public String toString() {
        return String.format("bar1: %s, bar2: %s, baz1: %s, baz2: %s", bar1.getInfo(), bar2.getInfo(), baz1.getInfo(), baz2.getInfo());
    }
}
