package software.bigbade.battlesnake.ai;

import software.bigbade.battlesnake.Battlesnake;
import software.bigbade.battlesnake.game.BattlesnakeGame;
import software.bigbade.battlesnake.game.GameMove;
import software.bigbade.battlesnake.game.Snake;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class AIManager {
    private final Set<IAITask> tasks = new TreeSet<>(Comparator.comparingInt(IAITask::getPriority));

    public AIManager() {
        tasks.add(new AvoidWalls());
        tasks.add(new AIGetFood());
    }

    public GameMove getMove(BattlesnakeGame game, Snake snake, boolean main) {
        Map<GameMove, Double> moves = new EnumMap<>(GameMove.class);
        for(GameMove move : GameMove.values()) {
            moves.put(move, 1d);
        }
        for(IAITask task : tasks) {
            if(main || task.runOnOthers()) {
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
