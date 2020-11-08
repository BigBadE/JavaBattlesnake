package software.bigbade.battlesnake.game;

import com.google.gson.JsonObject;
import software.bigbade.battlesnake.ai.AIManager;
import software.bigbade.battlesnake.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private final Map<Integer, BattlesnakeGame> games = new HashMap<>();

    private final AIManager aiManager = new AIManager();

    public GameMove tickGame(JsonObject object) {
        JsonObject board = object.get("board").getAsJsonObject();

        BattlesnakeGame game = games.get(JsonUtil.getId(object.get("game")));

        game.update(board);

        return aiManager.getMove(game, game.getSnake("walllover"), true);
    }

    public void createGame(JsonObject object) {
        JsonObject game = object.get("game").getAsJsonObject();
        JsonObject board = object.get("board").getAsJsonObject();

        BattlesnakeGame battlesnakeGame = new BattlesnakeGame(board);

        games.put(JsonUtil.getId(game), battlesnakeGame);
    }

    public void endGame(JsonObject object) {
        games.remove(JsonUtil.getId(object.get("game")));
    }
}
