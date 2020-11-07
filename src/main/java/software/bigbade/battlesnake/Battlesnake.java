package software.bigbade.battlesnake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bigbade.battlesnake.arguments.SnakeArguments;
import software.bigbade.battlesnake.network.NetworkUtils;
import software.bigbade.battlesnake.network.ResponseHandler;

public final class Battlesnake {
    public static final Logger LOGGER = LoggerFactory.getLogger(Battlesnake.class);

    private final ResponseHandler responseHandler;

    public static void main(String[] args) {
        new Battlesnake(args);
    }

    private Battlesnake(String[] args) {
        responseHandler = new ResponseHandler(new SnakeArguments(args));
        NetworkUtils.setupNetworkreciever(responseHandler);
    }
}
