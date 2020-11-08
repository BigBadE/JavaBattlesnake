package software.bigbade.battlesnake.network;

import software.bigbade.battlesnake.Battlesnake;
import spark.Spark;

public final class NetworkUtils {
    private NetworkUtils() {}

    public static void setupNetworkreciever(ResponseHandler responseHandler) {
        String port = System.getProperty("PORT");
        if (port != null) {
            Battlesnake.LOGGER.info("Found system provided port: {}", port);
        } else {
            port = "8080";
            Battlesnake.LOGGER.info("Using default port: {}", port);
        }
        Spark.port(Integer.parseInt(port));
        //Spark.get("/",  responseHandler::process, responseHandler.getGson()::toJson);
        Spark.post("/start", responseHandler::process, responseHandler.getGson()::toJson);
        Spark.post("/move", responseHandler::process, responseHandler.getGson()::toJson);
        Spark.post("/end", responseHandler::process, responseHandler.getGson()::toJson);
    }
}
