package software.bigbade.battlesnake.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distanceSquared(Position other) {
        return Math.pow(x-other.getX(), 2)-Math.pow(y-other.getY(), 2);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Position)) {
            return false;
        }
        Position pos = (Position) obj;
        return pos.getX() == x && pos.getY() == y;
    }

    @Override
    public int hashCode() {
        return x * y;
    }
}
