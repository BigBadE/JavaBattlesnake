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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AIManager {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private final List<IAITask> tasks = new ArrayList<>();

    public AIManager() {
        tasks.add(new TaskAvoidWalls());
        tasks.add(new TaskDontLoseHeadToHeads());
        tasks.add(new TaskGetFood());
        tasks.add(new TaskDontTrapYourself());
    }

    public GameMove getMove(BattlesnakeGame game, Snake snake, boolean main) {
        Map<GameMove, Double> moves = Collections.synchronizedMap(new EnumMap<>(GameMove.class));
        for(GameMove move : GameMove.values()) {
            moves.put(move, 1d);
        }

        Future<?>[] futures = new Future<?>[tasks.size()];

        for(int i = 0; i < tasks.size(); i++) {
            IAITask task = tasks.get(i);
            if(main || task.runOnOthers()) {
                futures[i] = executor.submit(() -> task.executeTask(moves, game, this, snake));
            }
        }

        //Make sure all AI tasks have executed in the correct order.
        for(Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                Battlesnake.LOGGER.error("Error running AI task", e.getCause());
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
