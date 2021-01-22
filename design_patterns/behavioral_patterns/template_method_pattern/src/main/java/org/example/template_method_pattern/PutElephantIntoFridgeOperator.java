package org.example.template_method_pattern;

public abstract class PutElephantIntoFridgeOperator {
    public void openFridgeDoor() {
        System.out.println("Open the fridge door.");
    }
    
    public void putElephantIn() {
        System.out.println("Put the elephant in.");
    }

    public void closeFridgeDoor() {
        System.out.println("Close the fridge door.");
    }

    public void startOperation() {
        openFridgeDoor();
        putElephantIn();
        closeFridgeDoor();
    }
}
