package org.example;

public class Bar {

    public void needBefore() {
        System.out.println("> needBefore");
    }

    public void needAfter() {
        System.out.println("> needAfter");
    }
    
    public void needAround() {
        System.out.println("> needAround");
    }

    public void needBeforeAfterAround() {
        System.out.println("> needBeforeAfterAround");
    }

    public String needAfterThrowing(int x) throws Exception {
        System.out.println("> needAfterThrowing");
        if (x == 1) {
            throw new Exception("throwing");
        }
        return "returning2";
    }

    public String needAfterReturning() {
        System.out.println("> needAfterReturning");
        return "returning";
    }
}
