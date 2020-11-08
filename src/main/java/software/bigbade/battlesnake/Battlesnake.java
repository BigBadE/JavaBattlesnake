package software.bigbade.battlesnake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bigbade.battlesnake.arguments.SnakeArguments;
import software.bigbade.battlesnake.game.GameManager;
import software.bigbade.battlesnake.network.NetworkUtils;
import software.bigbade.battlesnake.network.ResponseHandler;

public final class Battlesnake {
    public static final Logger LOGGER = LoggerFactory.getLogger(Battlesnake.class);

    private final GameManager gameManager = new GameManager();

    public static void main(String[] args) {
        Battlesnake battlesnake = new Battlesnake();
        battlesnake.setup();
    }

    private void setup() {
        ResponseHandler responseHandler = new ResponseHandler(new SnakeArguments(), gameManager);
        NetworkUtils.setupNetworkreciever(responseHandler);
    }

    public static void info(String message, Object... args) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info(message, args);
        }
    }
}
