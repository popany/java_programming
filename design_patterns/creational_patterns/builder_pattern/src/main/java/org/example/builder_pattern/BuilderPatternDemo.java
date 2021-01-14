// https://refactoring.guru/design-patterns/builder

package org.example.builder_pattern;

public class BuilderPatternDemo {
    public static void main(String[] args) {
        Director director = new Director();
        FastFoodBuilder fastFoodBuilder = new FastFoodBuilder();
        FoodContainerBuilder foodContainerBuilder = new FoodContainerBuilder();

        director.setBuilder(fastFoodBuilder);
        director.constructChineseFastFood();
        director.setBuilder(foodContainerBuilder);
        director.constructChineseFastFood();

        FastFood chineseFastFood = fastFoodBuilder.getProduct();
        FoodContainers chineseFoodContainers = foodContainerBuilder.getProduct();
        System.out.println("Chinese Fast Food:");
        System.out.println(String.format("%s  %s", chineseFastFood.getStapleFood(), chineseFastFood.getDrink()));
        System.out.println(String.format("%s  %s\n", chineseFoodContainers.getStapleFoodContainer(), chineseFoodContainers.getDrinkContainer()));

        director.setBuilder(fastFoodBuilder);
        director.constructWesternFastFood();
        director.setBuilder(foodContainerBuilder);
        director.constructWesternFastFood();

        FastFood westernFastFood = fastFoodBuilder.getProduct();
        FoodContainers westernFoodContainers = foodContainerBuilder.getProduct();
        System.out.println("Western Fast Food:");
        System.out.println(String.format("%s  %s", westernFastFood.getStapleFood(), westernFastFood.getDrink()));
        System.out.println(String.format("%s  %s\n", westernFoodContainers.getStapleFoodContainer(), westernFoodContainers.getDrinkContainer()));
    }
}
