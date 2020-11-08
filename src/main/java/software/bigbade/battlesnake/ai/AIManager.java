package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AIManager {
    private final List<IAITask> tasks = new ArrayList<>();

    public AIManager() {
        tasks.add(new TaskAvoidWalls());
        tasks.add(new TaskGetFood());
        tasks.add(new TaskDontTrapYourself());
    }

    public GameMove getMove(BattlesnakeGame game, Snake snake, boolean main) {
        Map<GameMove, Double> moves = new EnumMap<>(GameMove.class);
        for(GameMove move : GameMove.values()) {
            moves.put(move, 1d);
        }
        for(IAITask task : tasks) {
            if(main || task.runOnOthers()) {
                Battlesnake.info("Calling task " + task);
                task.executeTask(moves, game, snake);
            }
        }
        double max = Collections.max(moves.values());
        if(max == 0) {
            Battlesnake.info("Trapped!");
            return GameMove.UP;
        }
        return moves.entrySet().stream().filter(entry -> entry.getValue() == max).findAny()
                .orElseThrow(() -> new IllegalStateException("No move has the max value!")).getKey();
    }
}
