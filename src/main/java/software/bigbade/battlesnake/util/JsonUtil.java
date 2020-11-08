package software.bigbade.battlesnake.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class JsonUtil {
    private JsonUtil() {}

    public static Position getPosition(JsonObject object) {
        return new Position(object.get("x").getAsInt(), object.get("y").getAsInt());
    }

    public static int getId(JsonElement object) {
        return object.getAsJsonObject().get("id").getAsInt();
    }
}
