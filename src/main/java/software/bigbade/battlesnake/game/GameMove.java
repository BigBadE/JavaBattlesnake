package software.bigbade.battlesnake.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.bigbade.battlesnake.util.Position;

@Getter
@RequiredArgsConstructor
public enum GameMove {
    UP("up", new Position(0, -1)),
    RIGHT("right", new Position(1, 0)),
    DOWN("down", new Position(0, 1)),
    LEFT("left", new Position(-1, 0));

    private final String key;
    private final Position relativePosition;

    public Position getRelative(Position position) {
        return position.subtract(relativePosition);
    }
}
