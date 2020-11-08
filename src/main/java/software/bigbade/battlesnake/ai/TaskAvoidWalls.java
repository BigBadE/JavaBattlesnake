package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.List;
import java.util.Map;

public class TaskAvoidWalls implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, Snake snake) {
        for (GameMove move : GameMove.values()) {
            Position relative = move.getRelative(snake.getHead());
            if (relative.getX() > game.getSize().getX() || relative.getY() > game.getSize().getY()
                    || relative.getX() == -1 || relative.getY() == -1
                    || game.getBoard()[relative.getX()][relative.getY()]) {
                moves.replace(move, 0d);
            }
        }
    }

    //TODO possibly optimize this.
    public static boolean touchingBody(List<Snake> snakes, Position position) {
        for(Snake testing : snakes) {
            for(Position body : testing.getBody()) {
                if(body.equals(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean runOnOthers() {
        return true;
    }
}
