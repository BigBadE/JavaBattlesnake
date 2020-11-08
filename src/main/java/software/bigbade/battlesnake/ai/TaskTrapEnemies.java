package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;

import java.util.Map;

public class TaskTrapEnemies implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, AIManager manager, Snake snake) {
        for(Snake testing : game.getSnakes()) {
            if(!testing.equals(game.getSnake())) {
                GameMove nextMove = manager.getMove(game, testing, false);
                TaskGetFood.moveTowardsPosition(moves, game, nextMove.getRelative(testing.getHead()));
            }
        }
    }

    @Override
    public boolean runOnOthers() {
        return false;
    }
}
