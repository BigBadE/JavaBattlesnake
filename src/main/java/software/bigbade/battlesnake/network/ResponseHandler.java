package software.bigbade.battlesnake.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.arguments.SnakeArguments;
import software.bigbade.battlesnake.game.GameManager;
import software.bigbade.battlesnake.game.GameMove;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("unused")
@RequiredArgsConstructor
public class ResponseHandler {
    /**
     * For the start/end request
     */
    private static final Map<String, String> EMPTY = new HashMap<>();

    @Getter
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final SnakeArguments arguments;
    private final GameManager gameManager;

    /**
     * Generic processor that prints out the request and response from the methods.
     *
     * @param request Incoming request
     * @param response Response modifier
     * @return Content to send to the Battlesnake server
     */
    public Map<String, String> process(Request request, Response response) {
        JsonObject parsedRequest = JsonParser.parseString(request.body()).getAsJsonObject();
        String uri = request.uri();

        Battlesnake.info("{} called with: {}", uri, request.body());

        Map<String, String> snakeResponse;
        switch (uri) {
            case "/":
                snakeResponse = index();
                break;
            case "/start":
                snakeResponse = start(parsedRequest);
                break;
            case "/move":
                snakeResponse = move(parsedRequest);
                break;
            case "/end":
                snakeResponse = end(parsedRequest);
                break;
            default:
                throw new IllegalAccessError("Strange call made to the snake: " + uri);
        }

        Battlesnake.info("Responding with: {}", gson.toJson(snakeResponse));

        return snakeResponse;
    }


    /**
     * This method is called everytime your Battlesnake is entered into a game.
     * <p>
     * Use this method to decide how your Battlesnake is going to look on the board.
     *
     * @return a response back to the engine containing the Battlesnake setup
     * values.
     */
    public Map<String, String> index() {
        Map<String, String> response = new HashMap<>();
        response.put("apiversion", "1");
        response.put("author", arguments.getAuthor());
        response.put("color", arguments.getColor());
        response.put("headType", arguments.getHeadType());
        response.put("tailType", arguments.getTailType());
        return response;
    }

    /**
     * This method is called everytime your Battlesnake is entered into a game.
     * <p>
     * Use this method to decide how your Battlesnake is going to look on the board.
     *
     * @param startRequest a JSON data map containing the information about the game
     *                     that is about to be played.
     * @return responses back to the engine are ignored.
     */
    public Map<String, String> start(JsonObject startRequest) {
        Battlesnake.LOGGER.info("START");
        gameManager.createGame(startRequest);
        return EMPTY;
    }

    /**
     * This method is called on every turn of a game. It's how your snake decides
     * where to move.
     * <p>
     * Valid moves are "up", "down", "left", or "right".
     *
     * @param moveRequest a map containing the JSON sent to this snake. Use this
     *                    data to decide your next move.
     * @return a response back to the engine containing Battlesnake movement values.
     */
    public Map<String, String> move(JsonObject moveRequest) {
        Battlesnake.info("Data: {}", gson.toJson(moveRequest));

        GameMove move = gameManager.tickGame(moveRequest);

        Battlesnake.info("MOVE {}", move);

        Map<String, String> response = new HashMap<>();
        response.put("move", move.getKey());
        return response;
    }

    /**
     * This method is called when a game your Battlesnake was in ends.
     * <p>
     * It is purely for informational purposes, you don't have to make any decisions
     * here.
     *
     * @param endRequest a map containing the JSON sent to this snake. Use this data
     *                   to know which game has ended
     * @return responses back to the engine are ignored.
     */
    public Map<String, String> end(JsonObject endRequest) {
        Battlesnake.LOGGER.info("END");
        return EMPTY;
    }
}
