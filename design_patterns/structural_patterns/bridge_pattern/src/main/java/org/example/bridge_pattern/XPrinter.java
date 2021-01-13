package org.example.bridge_pattern;

public class XPrinter implements Printer {
    public void print(String text) {
        System.out.println(text.replace('.', 'x'));
    }
}
