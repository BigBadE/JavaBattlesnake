package software.bigbade.battlesnake.arguments;

import lombok.Getter;

@Getter
public class SnakeArguments {
    private final String author = getProperty("author", "Big_Bad_E");
    private final String color = getProperty("color", "#0066FF");
    private final String headType = getProperty("headType", "silly");
    private final String tailType = getProperty("tailType", "skinny");

    private static String getProperty(String name, String defaultValue) {
        String found = System.getProperty(name);
        return found == null ? defaultValue : name;
    }
}
