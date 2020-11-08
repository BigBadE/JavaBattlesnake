package software.bigbade.battlesnake.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import software.bigbade.battlesnake.util.Position;

@Getter
@RequiredArgsConstructor
public enum GameMove {
    UP("up", 0, 1),
    RIGHT("right", 1, 0),
    DOWN("down", 0, -1),
    LEFT("left", -1, 0);

    private final String key;
    private final int x;
    private final int y;

    public Position getRelative(Position position) {
        return new Position(position.getX()+x, position.getY()+y);
    }
}
