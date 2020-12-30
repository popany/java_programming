package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Foo {

    @Autowired
    @Qualifier("bar1")
    Bar bar1;
    Bar bar2;

    @Autowired
    @Qualifier("baz1")
    Baz baz1;
    Baz baz2;

    @Autowired
    public Foo(@Qualifier("bar2") Bar bar, @Qualifier("baz2") Baz baz) {
        this.bar2 = bar;
        this.baz2 = baz;
    }

    public String toString() {
        return String.format("Bar1: %s, Bar2: %s, Baz1: %s, Baz2: %s", bar1.getInfo(), bar2.getInfo(), baz1.getInfo(), baz2.getInfo());
    }
}
