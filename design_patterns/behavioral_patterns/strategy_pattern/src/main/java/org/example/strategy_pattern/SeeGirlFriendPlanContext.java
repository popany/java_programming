package org.example.strategy_pattern;

import org.example.strategy_pattern.RouteStrategy;

public class SeeGirlFriendPlanContext {
    RouteStrategy strategy;
    String map = new String("  |-- your girl friend\n  g            |-- water\n---------\\   /-|--------\n~ ~ ~ ~ ~|   | ~ ~ ~ ~ ~\n ~ ~ ~ ~ |   |~ ~ ~ ~ ~ \n~ ~ ~ ~ ~|   | ~ ~ ~ ~ ~\n---------/   \\----------\n  b          |--- bridge\n  |-- you"); 

    public void setStrategy(RouteStrategy strategy) {
        this.strategy = strategy;
    }

    public void printMap() {
        System.out.println(map);
    }
    
    public void printPlan() {
        System.out.println(strategy.getRoute(map));
    }
}
