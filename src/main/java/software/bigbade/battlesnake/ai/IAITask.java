package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;

import java.util.Map;

public interface IAITask {
    void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, Snake snake);

    boolean runOnOthers();
}
