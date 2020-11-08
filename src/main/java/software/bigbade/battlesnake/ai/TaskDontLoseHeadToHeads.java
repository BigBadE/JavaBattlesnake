package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.Map;

public class TaskDontLoseHeadToHeads implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, AIManager manager, Snake snake) {
        for (Snake testing : game.getSnakes()) {
            if (!testing.equals(snake) && testing.getHead().distanceSquared(snake.getHead()) == 1
                    && testing.getLength() >= snake.getLength()) {
                Position change = testing.getHead().subtract(snake.getHead());
                for (GameMove move : GameMove.values()) {
                    if (move.getRelativePosition().equals(change)) {
                        moves.replace(move, 0d);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean runOnOthers() {
        return false;
    }
}
