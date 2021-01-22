package org.example.strategy_pattern;

public class SwimmingStrategy implements RouteStrategy {
    public String getRoute(String map) {
        return new String("  |-- your girl friend\n  g	           |-- water\n--^------\\   /-|--------\n~ | ~ ~ ~|   | ~ ~ ~ ~ ~\n ~|~ ~ ~ |   |~ ~ ~ ~ ~ \n~ | ~ ~ ~|   | ~ ~ ~ ~ ~ \n--|------/   \\----------\n  b          |--- bridge\n  |-- you");
    }
}
