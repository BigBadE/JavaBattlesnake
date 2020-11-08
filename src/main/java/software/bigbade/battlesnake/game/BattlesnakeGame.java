package software.bigbade.battlesnake.game;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import software.bigbade.battlesnake.util.JsonUtil;
import software.bigbade.battlesnake.util.Position;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BattlesnakeGame {
    private final Position size;

    private final List<Position> food = new ArrayList<>();
    private final List<Position> hazards = new ArrayList<>();
    private final List<Snake> snakes = new ArrayList<>();

    public BattlesnakeGame(JsonObject board) {
        size = new Position(board.get("width").getAsInt(), board.get("height").getAsInt());

        for(JsonElement element : board.get("food").getAsJsonArray()) {
            JsonObject object = (JsonObject) element;
            food.add(JsonUtil.getPosition(object));
        }

        for(JsonElement element : board.get("hazards").getAsJsonArray()) {
            JsonObject object = (JsonObject) element;
            hazards.add(JsonUtil.getPosition(object));
        }

        int numb = 1;
        for(JsonElement element : board.get("snakes").getAsJsonArray()) {
            JsonObject object = (JsonObject) element;
            List<Position> body = new ArrayList<>();
            for(JsonElement bodyElement : object.get("body").getAsJsonArray()) {
                JsonObject bodyObject = (JsonObject) bodyElement;
                body.add(JsonUtil.getPosition(bodyObject));
            }
            JsonObject head = object.get("head").getAsJsonObject();
            snakes.add(new Snake(object.get("id").getAsString(),
                    JsonUtil.getPosition(head), body,
                    object.get("health").getAsInt(), object.get("length").getAsInt(),
                    object.has("squad") ? object.get("squad").getAsInt() : numb++));
        }
    }

    public void update(JsonObject board) {
        food.clear();
        for(JsonElement element : board.get("food").getAsJsonArray()) {
            JsonObject object = (JsonObject) element;
            food.add(JsonUtil.getPosition(object));
        }

        for(JsonElement element : board.get("snakes").getAsJsonArray()) {
            JsonObject object = (JsonObject) element;
            Snake snake = getSnake(object.get("id").getAsString());
            List<Position> body = new ArrayList<>();
            for(JsonElement bodyElement : object.get("body").getAsJsonArray()) {
                JsonObject bodyObject = (JsonObject) bodyElement;
                body.add(JsonUtil.getPosition(bodyObject));
            }
            snake.setBody(body);
            snake.setHealth(object.get("health").getAsInt());
            snake.setLength(object.get("length").getAsInt());
            snake.setHead(JsonUtil.getPosition(object.get("head").getAsJsonObject()));
        }
    }

    public Snake getSnake(String id) {
        for(Snake snake : snakes) {
            if(snake.getId().equals(id)) {
                return snake;
            }
        }
        throw new IllegalArgumentException("No snake with ID " + id);
    }
}
