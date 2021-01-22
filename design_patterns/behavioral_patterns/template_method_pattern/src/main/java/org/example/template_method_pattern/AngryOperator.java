package org.example.template_method_pattern;

import org.example.template_method_pattern.PutElephantIntoFridgeOperator;

public class AngryOperator extends PutElephantIntoFridgeOperator {

    public void openFridgeDoor() {
        System.out.println("Open the fridge door roughly.");
    }
    
    public void closeFridgeDoor() {
        System.out.println("Close the fridge door hard.");
    }
}
