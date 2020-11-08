package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;
import software.bigbade.battlesnake.util.Position;

import java.util.Map;

public class TaskDontTrapYourself implements IAITask {
    private double cachedRoot = -1;
    @Override
    public void executeTask(Map<GameMove, Double> moves, BattlesnakeGame game, AIManager manager, Snake snake) {
        //Remove the tail to simulate snake movement. Could get juked if the snake eats an apple.
        for (Snake found : game.getSnakes()) {
            Position tail = found.getBody().get(0);
            game.getBoard()[tail.getX()][tail.getY()] = false;
        }

        for (GameMove move : GameMove.values()) {
            if (moves.get(move) == 0) {
                continue;
            }
            if(cachedRoot == -1) {
                cachedRoot = Math.pow(3, 1d/(game.getSize().getX()*game.getSize().getY()));
            }
            int empty = fillArea(move.getRelative(snake.getHead()), game.getBoard());
            Battlesnake.info("EMPTY: {}, MOVE {}", empty, move);
            moves.replace(move, (5*Math.pow(cachedRoot, empty)-5) * moves.get(move));
        }

        for (Snake found : game.getSnakes()) {
            Position tail = found.getBody().get(0);
            game.getBoard()[tail.getX()][tail.getY()] = true;
        }
    }

    public static int fillArea(Position position, boolean[][] arr) {
        int maxX = arr.length - 1;
        int maxY = arr[0].length - 1;
        boolean[][] checked = new boolean[maxX + 1][maxY + 1];
        int[][] stack = new int[(maxX + 1) * (maxY + 1)][2];
        int index = 0;
        int found = 0;

        stack[0][0] = position.getX();
        stack[0][1] = position.getY();
        checked[position.getX()][position.getY()] = true;

        while (index >= 0) {
            position.setX(stack[index][0]);
            position.setY(stack[index][1]);
            index--;
            found++;

            if ((position.getX() > 0) && !arr[position.getX() - 1][position.getY()]
                    && !checked[position.getX() - 1][position.getY()]) {
                checked[position.getX() - 1][position.getY()] = true;
                index++;
                stack[index][0] = position.getX() - 1;
                stack[index][1] = position.getY();
            }

            if ((position.getX() < maxX) && !arr[position.getX() + 1][position.getY()]
                    && !checked[position.getX() + 1][position.getY()]) {
                checked[position.getX() + 1][position.getY()] = true;
                index++;
                stack[index][0] = position.getX() + 1;
                stack[index][1] = position.getY();
            }

            if ((position.getY() > 0) && !arr[position.getX()][position.getY() - 1]
                    && !checked[position.getX()][position.getY() - 1]) {
                checked[position.getX()][position.getY() - 1] = true;
                index++;
                stack[index][0] = position.getX();
                stack[index][1] = position.getY() - 1;
            }

            if ((position.getY() < maxY) && !arr[position.getX()][position.getY() + 1]
                    && !checked[position.getX()][position.getY() + 1]) {
                checked[position.getX()][position.getY() + 1] = true;
                index++;
                stack[index][0] = position.getX();
                stack[index][1] = position.getY() + 1;
            }
        }
        return found;
    }

    @Override
    public boolean runOnOthers() {
        return true;
    }
}
