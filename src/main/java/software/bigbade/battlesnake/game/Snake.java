package software.bigbade.battlesnake.game;

import lombok.Getter;
import lombok.Setter;
import software.bigbade.battlesnake.util.Position;

import java.util.List;

@Getter
public class Snake {
    private final String id;
    private final int squad;

    @Setter
    private Position head;
    @Setter
    private List<Position> body;

    @Setter
    private int health;
    @Setter
    private int length;

    public Snake(String id, Position head, List<Position> body, int health, int length, int squad) {
        this.id = id;
        this.head = head;
        this.body = body;
        this.health = health;
        this.length = length;
        this.squad = squad;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Snake)) {
            return false;
        }
        return ((Snake) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
