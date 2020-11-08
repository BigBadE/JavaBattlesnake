package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaskDontTrapYourself implements IAITask {
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, Snake snake) {
        //Remove the tail to simulate snake movement. Could get juked if the snake eats an apple.
        List<Position> extraTails = new ArrayList<>();
        for (Snake found : game.getSnakes()) {
            int index = found.getBody().size() - 1;
            extraTails.add(found.getBody().get(index));
            found.getBody().remove(index);
        }

        for (GameMove move : GameMove.values()) {
            if (moves.get(move) == 0) {
                continue;
            }
            int empty = floodFill(move.getRelative(snake.getHead()), game, new HashSet<>());
            if (empty == 0) {
                Battlesnake.info("FILL: EMPTY for {}", move);
                moves.replace(move, 0d);
                continue;
            }
            moves.replace(move, game.getSize().getX() * game.getSize().getY() / empty * 5 * moves.get(move));
            Battlesnake.info("FILL: {} for {}",
                    game.getSize().getX() * game.getSize().getY() / empty * 5 * moves.get(move), move);
        }

        for (int i = 0; i < game.getSnakes().size(); i++) {
            Snake found = game.getSnakes().get(i);
            found.getBody().add(extraTails.get(i));
        }

    }

    private int floodFill(Position position, BattlesnakeGame game, Set<Position> checked) {
        int found = 0;
        Battlesnake.info("{}, {}, {}, {}, {}, {}", !checked.contains(position),
                TaskAvoidWalls.touchingBody(game.getSnakes(), position), position.getX() >= 0,
                position.getX() < game.getSize().getX(), position.getY() >= 0, position.getY() < game.getSize().getY());
        if (!checked.contains(position) && TaskAvoidWalls.touchingBody(game.getSnakes(), position)
                && position.getX() >= 0 && position.getX() < game.getSize().getX()
                && position.getY() >= 0 && position.getY() < game.getSize().getY()) {
            for (GameMove move : GameMove.values()) {
                Position relative = move.getRelative(position);

                checked.add(relative);
                floodFill(relative, game, checked);
            }
            found += 1;
        }
        return found;
    }

    @Override
    public boolean runOnOthers() {
        return true;
    }
}
