package tigerislandserver.gameplay;

import java.util.ArrayList;
import java.util.List;

public class TurnSynchronizer {
    List<GameThread> games = new ArrayList<>();
    synchronized public void addGame(GameThread game) {
        games.add(game);
    }

    synchronized public void checkGamesReadyToMove() {
        for (GameThread game : games ) {
            if ( !game.hasTurnWaiting() ) {
                return;
            }
        }

        // All games are ready to move
        for ( GameThread game : games ) {
            game.makeMove();
        }
    }
}
