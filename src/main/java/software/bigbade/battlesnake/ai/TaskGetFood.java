package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.Map;

public class TaskGetFood implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, AIManager manager, Snake snake) {

        for(Position position : game.getFood()) {
            moveTowardsPosition(moves, game, position);
        }
    }

    private static double getModifier(BattlesnakeGame game, Position food, GameMove direction) {
        Position headPosition = game.getSnake().getHead();
        double distance = food.distanceSquared(headPosition);
        if(distance < 2*2) {
            //We wanna grab any close food
            return 3;
        }
        double axisDistance = (direction == GameMove.UP || direction == GameMove.DOWN) ?
                headPosition.getY()-food.getY() : headPosition.getX()-food.getX();
        axisDistance = Math.abs(axisDistance)+1;
        return game.getSize().getX()/axisDistance/distance+(101f/(game.getSnake().getHealth()+1));
    }

    public static void moveTowardsPosition(Map<GameMove, Double> moves, BattlesnakeGame game, Position position) {
        //Get closest food
        Position headPosition = game.getSnake().getHead();
        switch (Integer.compare(position.getX(), headPosition.getX())) {
            case 1:
                moves.replace(GameMove.RIGHT, moves.get(GameMove.RIGHT)*
                        getModifier(game, position, GameMove.RIGHT));
                break;
            case 0:
                break;
            case -1:
                moves.replace(GameMove.LEFT, moves.get(GameMove.LEFT)*
                        getModifier(game, position, GameMove.LEFT));
        }
        switch (Integer.compare(position.getY(), headPosition.getY())) {
            case 1:
                moves.replace(GameMove.DOWN, moves.get(GameMove.DOWN)*
                        getModifier(game, position, GameMove.DOWN));
                break;
            case 0:
                break;
            case -1:
                moves.replace(GameMove.UP, moves.get(GameMove.UP)*
                        getModifier(game, position, GameMove.UP));
        }
    }

    @Override
    public boolean runOnOthers() {
        return true;
    }
}
