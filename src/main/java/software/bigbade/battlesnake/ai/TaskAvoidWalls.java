package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.Map;

public class TaskAvoidWalls implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, AIManager manager, Snake snake) {
        //Remove the tail to simulate snake movement. Could get juked if the snake eats an apple.
        for (Snake found : game.getSnakes()) {
            if(found.getLength() > 1) {
                Position tail = found.getBody().get(found.getBody().size() - 1);
                game.getBoard()[tail.getX()][tail.getY()] = false;
            }
        }

        for (GameMove move : GameMove.values()) {
            Position relative = move.getRelative(snake.getHead());
            if (relative.getX() > game.getSize().getX() || relative.getY() > game.getSize().getY()
                    || relative.getX() == -1 || relative.getY() == -1
                    || game.getBoard()[relative.getX()][relative.getY()]) {
                Battlesnake.info("Wall at {}", move);
                moves.replace(move, 0d);
            }
        }

        for (Snake found : game.getSnakes()) {
            if(found.getLength() > 1) {
                Position tail = found.getBody().get(found.getBody().size() - 1);
                game.getBoard()[tail.getX()][tail.getY()] = true;
            }
        }
    }

    @Override
    public boolean runOnOthers() {
        return true;
    }
}
