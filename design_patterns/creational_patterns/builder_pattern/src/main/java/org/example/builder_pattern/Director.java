package org.example.builder_pattern;

public class Director {
    Builder builder;

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }

    public void constructChineseFastFood() {
        builder.reset();
        builder.setStapleFood("You Tiao");
        builder.setDrink("Soy milk");
    }

    public void constructWesternFastFood() {
        builder.reset();
        builder.setStapleFood("Fried chicken");
        builder.setDrink("Cola");
    }
}
