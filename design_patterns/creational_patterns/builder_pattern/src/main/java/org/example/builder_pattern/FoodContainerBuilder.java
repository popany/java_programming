package org.example.builder_pattern;

public class FoodContainerBuilder implements Builder {
    FoodContainers foodContainers = new FoodContainers();

    public void reset() {
        foodContainers = new FoodContainers();
    }

    public FoodContainers getProduct() {
        return foodContainers;
    }

    public void setStapleFood(String stapleFood) {
        switch (stapleFood) {
            case "You Tiao":
                foodContainers.setStapleFoodContainer("\\========/");
                break;
            case "Fried chicken":
                foodContainers.setStapleFoodContainer("\\#############/");
                break;
            default:
                foodContainers.setStapleFoodContainer("none");
        }
    }

    public void setDrink(String drink) {
        switch (drink) {
            case "Soy milk":
                foodContainers.setDrinkContainer("|________|");
                break;
            case "Cola":
                foodContainers.setDrinkContainer("\\____/");
                break;
            default:
                foodContainers.setDrinkContainer("none");
        }
    }

}
