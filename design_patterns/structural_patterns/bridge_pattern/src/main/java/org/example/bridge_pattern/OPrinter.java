package org.example.bridge_pattern;

public class OPrinter implements Printer {
    public void print(String text) {
        System.out.println(text.replace('.', 'o'));
    }
}
