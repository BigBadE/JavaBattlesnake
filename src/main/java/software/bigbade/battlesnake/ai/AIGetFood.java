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
                    moves.replace(GameMove.RIGHT, moves.get(GameMove.RIGHT)*
                            getModifier(game, food, GameMove.RIGHT));
                    break;
                case 0:
                    break;
                case -1:
                    moves.replace(GameMove.LEFT, moves.get(GameMove.LEFT)*
                            getModifier(game, food, GameMove.LEFT));
            }
            switch (Integer.compare(food.getY(), headPosition.getY())) {
                case 1:
                    moves.replace(GameMove.DOWN, moves.get(GameMove.DOWN)*
                            getModifier(game, food, GameMove.DOWN));
                    break;
                case 0:
                    break;
                case -1:
                    moves.replace(GameMove.UP, moves.get(GameMove.UP)*
                            getModifier(game, food, GameMove.UP));
            }
        }
    }

    public double getModifier(BattlesnakeGame game, Position food, GameMove direction) {
        Position headPosition = game.getSnake().getHead();
        double distance = food.distanceSquared(headPosition);
        if(distance < 2*2) {
            //We wanna grab any close food
            return 3;
        }
        double axisDistance = (direction == GameMove.UP || direction == GameMove.DOWN) ?
                headPosition.getY()-food.getY() : headPosition.getX()-food.getX();
        axisDistance = Math.abs(axisDistance)+1;
        Battlesnake.info("Getting move for {} ({})", direction,
                game.getSize().getX()/axisDistance/distance+(101f/(game.getSnake().getHealth()+1)));
        return game.getSize().getX()/axisDistance/distance+(101f/(game.getSnake().getHealth()+1));
    }

    @Override
    public boolean runOnOthers() {
        return true;
    }
}
