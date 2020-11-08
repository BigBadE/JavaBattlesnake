package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.Map;

public class AIGetFood implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, Snake snake) {
        //Get closest food
        Position headPosition = game.getSnake().getHead();
        for(Position food : game.getFood()) {
            switch (Integer.compare(food.getX(), headPosition.getX())) {
                case 1:
                    moves.replace(GameMove.LEFT, moves.get(GameMove.LEFT)*
                            getModifier(game, headPosition, GameMove.LEFT));
                    break;
                case 0:
                    break;
                case -1:
                    moves.replace(GameMove.RIGHT, moves.get(GameMove.RIGHT)*
                            getModifier(game, headPosition, GameMove.RIGHT));
            }
            switch (Integer.compare(food.getY(), headPosition.getY())) {
                case 1:
                    moves.replace(GameMove.UP, moves.get(GameMove.UP)*
                            getModifier(game, headPosition, GameMove.UP));
                    break;
                case 0:
                    break;
                case -1:
                    moves.replace(GameMove.DOWN, moves.get(GameMove.DOWN)*
                            getModifier(game, headPosition, GameMove.DOWN));
            }
        }
    }

    public double getModifier(BattlesnakeGame game, Position food, GameMove direction) {
        Position headPosition = game.getSnake().getHead();
        double distance = food.distanceSquared(headPosition);
        double axisDistance = (direction == GameMove.UP || direction == GameMove.DOWN) ?
                food.getY()-headPosition.getY() : food.getX()-headPosition.getX();
        axisDistance = Math.abs(axisDistance);
        Battlesnake.info("Getting move for {} (axis distance {}) for {}", direction, axisDistance,
                game.getSize().getX()/axisDistance/distance+(101f/(game.getSnake().getHealth()+1)));
        return game.getSize().getX()/axisDistance/distance+(101f/(game.getSnake().getHealth()+1));
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
