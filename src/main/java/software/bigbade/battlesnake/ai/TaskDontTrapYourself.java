package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaskDontTrapYourself implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, Snake snake) {
        long start = System.currentTimeMillis();
        //Remove the tail to simulate snake movement. Could get juked if the snake eats an apple.
        List<Position> extraTails = new ArrayList<>();
        for(Snake found : game.getSnakes()) {
            int index = found.getBody().size()-1;
            extraTails.add(found.getBody().get(index));
            found.getBody().remove(index);
        }

        for(GameMove move : GameMove.values()) {
            int empty = floodFill(move.getRelative(snake.getHead()), game);
            assert empty != 0;
            moves.replace(move, game.getSize().getX()*game.getSize().getY()/empty*5*moves.get(move));
        }

        for(int i = 0; i < game.getSnakes().size(); i++) {
            Snake found = game.getSnakes().get(i);
            found.getBody().add(extraTails.get(i));
        }
        System.out.println("FILL TIME: " + (System.currentTimeMillis()-start));
    }

    //TODO improve flood fill algorithm for finding empty space
    private int floodFill(Position position, BattlesnakeGame game) {
        int found = 0;
        Set<Position> valid = new HashSet<>();
        valid.add(position);
        Set<Position> checked = new HashSet<>();
        while (!valid.isEmpty()) {
            Set<Position> nextCycle = new HashSet<>();
            for (Position testing : valid) {
                if (!checked.contains(testing) && !TaskAvoidWalls.touchingBody(game.getSnakes(), testing)) {
                    checked.add(testing);
                    for (GameMove move : GameMove.values()) {
                        nextCycle.add(move.getRelative(testing));
                    }
                    found += 1;
                }
            }
            valid = nextCycle;
        }
        return found;
    }

    @Override
    public boolean runOnOthers() {
        return true;
    }
}
