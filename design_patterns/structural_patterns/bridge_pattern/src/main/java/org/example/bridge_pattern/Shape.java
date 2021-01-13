package org.example.bridge_pattern;

public abstract class Shape {
    Printer printer;

    abstract String getText();

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    public void print() {
        printer.print(getText());
    }
}
