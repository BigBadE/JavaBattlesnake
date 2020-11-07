package software.bigbade.battlesnake.arguments;

import lombok.Getter;
import software.bigbade.battlesnake.Battlesnake;

@Getter
public class SnakeArguments {
    private String author = "Big_Bad_E";
    private String color = "#888888";
    private String headType = "default";
    private String tailType = "default";

    public SnakeArguments(String[] args) {
        int i = 0;
        while(i < args.length) {
            switch (args[i++].toLowerCase()) {
                case "--author":
                    author = args[i++];
                    break;
                case "--color":
                    color = args[i++];
                    break;
                case "--headtype":
                    headType = args[i++];
                    break;
                case "--tailtype":
                    tailType = args[i++];
                    break;
                default:
                    Battlesnake.LOGGER.warn("Unknown argument: " + args[i-1]);
            }
        }
    }
}
