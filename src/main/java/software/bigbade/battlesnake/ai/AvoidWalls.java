package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.Map;

public class AvoidWalls implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, Snake snake) {
        for(GameMove move : GameMove.values()) {
            Position relative = move.getRelative(snake.getHead());
            Battlesnake.info("Testing direction: " + move + "(" + relative.getX() + ", " + relative.getY() + ")"
            + " size (" + game.getSize().getX() + ", " + game.getSize().getY() + ")");
            if(game.getHazards().contains(relative)
                    || relative.getX() > game.getSize().getX() || relative.getY() > game.getSize().getY()
                    || relative.getX() == -1 || relative.getY() == -1) {
                Battlesnake.info("Found wall: " + move);
                moves.replace(move, 0d);
            }
        }
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean runOnOthers() {
        return true;
    }
}
