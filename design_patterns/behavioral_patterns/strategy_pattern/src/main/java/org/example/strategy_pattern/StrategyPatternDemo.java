// https://refactoring.guru/design-patterns/strategy

package org.example.strategy_pattern;

import org.example.strategy_pattern.CrossBridgeStrategy;
import org.example.strategy_pattern.SeeGirlFriendPlanContext;
import org.example.strategy_pattern.SwimmingStrategy;

public class StrategyPatternDemo {
    public static void main(String[] args) {
        SeeGirlFriendPlanContext plan = new SeeGirlFriendPlanContext();
        System.out.println("You are going to see your girl friend:\n");
        plan.printMap();

        System.out.println("");
        System.out.println("Swimming strategy:\n");
        plan.setStrategy(new SwimmingStrategy());
        plan.printPlan();
        
        System.out.println("");
        System.out.println("Cross bridge strategy:\n");
        plan.setStrategy(new CrossBridgeStrategy());
        plan.printPlan();
    }
}
