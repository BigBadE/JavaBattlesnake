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
    private final Snake snake;
    private final Position size;

    private final List<Position> food = new ArrayList<>();
    private final List<Position> hazards = new ArrayList<>();
    private final List<Snake> snakes = new ArrayList<>();

    public BattlesnakeGame(JsonObject board, String snake) {
        size = new Position(board.get("width").getAsInt()-1, board.get("height").getAsInt()-1);

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
        this.snake = getSnakeByID(snake);
    }

    public void update(JsonObject board) {
        food.clear();
        for(JsonElement element : board.get("food").getAsJsonArray()) {
            JsonObject object = (JsonObject) element;
            food.add(JsonUtil.getPosition(object));
        }

        for(JsonElement element : board.get("snakes").getAsJsonArray()) {
            JsonObject object = (JsonObject) element;
            Snake updating = getSnakeByID(object.get("id").getAsString());
            List<Position> body = new ArrayList<>();
            for(JsonElement bodyElement : object.get("body").getAsJsonArray()) {
                JsonObject bodyObject = (JsonObject) bodyElement;
                body.add(JsonUtil.getPosition(bodyObject));
            }
            updating.setBody(body);
            updating.setHealth(object.get("health").getAsInt());
            updating.setLength(object.get("length").getAsInt());
            updating.setHead(JsonUtil.getPosition(object.get("head").getAsJsonObject()));
        }
    }

    public Snake getSnakeByID(String id) {
        for(Snake found : snakes) {
            if(found.getId().equals(id)) {
                return found;
            }
        }
        throw new IllegalArgumentException("No snake with ID " + id);
    }
}
