package tigerislandserver.gameplay;

import java.util.ArrayList;
import java.util.List;

public class TurnSynchronizer {
    List<GameThread> games = new ArrayList<>();
    synchronized public void addGame(GameThread game) {
        games.add(game);
    }

    synchronized public void checkGamesReadyToMove() {
        synchronized (games) {
            for (GameThread game : games) {
                if (game.isAlive() && !game.hasTurnWaiting()) {
                    // If a game is dead, then no point waiting on it.
                    // Once all living games have a turn waiting,
                    // Then we can tell them to make that turn in one batch.
                    return;
                }
            }

            // All games are ready to move
            for (GameThread game : games) {
                game.makeMove();
            }
        }
    }
}
