package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TaskDontTrapYourself implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, Snake snake) {
        //Remove the tail to simulate snake movement. Could get juked if the snake eats an apple.
        for (Snake found : game.getSnakes()) {
            Position tail = found.getBody().get(found.getBody().size() - 1);
            game.getBoard()[tail.getX()][tail.getY()] = false;
        }

        for (GameMove move : GameMove.values()) {
            if (moves.get(move) == 0) {
                continue;
            }
            int empty = floodFill(move.getRelative(snake.getHead()), game, new HashSet<>());
            Battlesnake.info("EMPTY: {} for {}", empty, move);
            if (empty == 0) {
                moves.replace(move, .01d);
                continue;
            }
            moves.replace(move, game.getSize().getX() * game.getSize().getY() / empty * 5 * moves.get(move));
        }

        for (Snake found : game.getSnakes()) {
            Position tail = found.getBody().get(found.getBody().size() - 1);
            game.getBoard()[tail.getX()][tail.getY()] = true;
        }
    }

    private int floodFill(Position position, BattlesnakeGame game, Set<Position> checked) {
        int found = 0;
        if (position.getX() >= 0 && position.getX() <= game.getSize().getX()
                && position.getY() >= 0 && position.getY() <= game.getSize().getY()
                && !checked.contains(position) && !game.getBoard()[position.getX()][position.getY()]) {
            found += 1;
            for (GameMove move : GameMove.values()) {
                Position relative = move.getRelative(position);

                checked.add(relative);
                found += floodFill(relative, game, checked);
            }
        }
        return found;
    }

    @Override
    public boolean runOnOthers() {
        return true;
    }
}
