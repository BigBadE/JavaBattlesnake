package software.bigbade.battlesnake.util;

import lombok.Getter;
import lombok.Setter;
import software.bigbade.battlesnake.Battlesnake;

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
        Battlesnake.info("({}-{})^2+({}-{})^2={}", x, other.getX(), y, other.getY(),
                Math.pow(x-other.getX(), 2)+Math.pow(y-other.getY(), 2));
        return Math.pow(x-other.getX(), 2)+Math.pow(y-other.getY(), 2);
    }

    public Position subtract(Position other) {
        return new Position(x-other.getX(), y-other.getY());
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
