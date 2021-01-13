// https://www.runoob.com/design-pattern/bridge-pattern.html

package org.example.bridge_pattern;

import org.example.bridge_pattern.Triangle;
import org.example.bridge_pattern.XPrinter;

public class BridgePatternDemo {
    public static void main(String[] args) {
        Shape tX = new Triangle();
        tX.setPrinter(new XPrinter());

        Shape tO = new Triangle();
        tO.setPrinter(new OPrinter());

        Shape sX = new Square();
        sX.setPrinter(new XPrinter());

        Shape sO = new Square();
        sO.setPrinter(new OPrinter());

        tX.print();
        tO.print();
        sX.print();
        sO.print();
    }
}
