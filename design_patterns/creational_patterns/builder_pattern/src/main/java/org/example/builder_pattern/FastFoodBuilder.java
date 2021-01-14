package org.example.builder_pattern;

public class FastFoodBuilder implements Builder {
    FastFood fastFood;

    public void reset() {
        fastFood = new FastFood();
    }

    public FastFood getProduct() {
        return fastFood;
    }

    public void setStapleFood(String stapleFood) {
        switch (stapleFood) {
            case "You Tiao":
                fastFood.setStapleFood("(You Tiao)");
                break;
            case "Fried chicken":
                fastFood.setStapleFood("(Fried chicken)");
                break;
            default:
                fastFood.setStapleFood("none");
        }
    }

    public void setDrink(String drink) {
        switch (drink) {
            case "Soy milk":
                fastFood.setDrink("[Soy milk]");
                break;
            case "Cola":
                fastFood.setDrink("[Cola]");
                break;
            default:
                fastFood.setDrink("none");
        }
    }
}
