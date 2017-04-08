package tigerislandserver.gameplay;

import tigerisland.player.Player;
import tigerisland.player.PlayerID;

/**
 * Created by christinemoore on 4/7/17.
 * Data Object to transfer
 * neceesary info to determine scorng from
 */
public class TournamentScoreboardData {
    private Player player;
    private int finalScore;

    TournamentScoreboardData(Player player, int finalScore){
        this.player = player;
        this.finalScore = finalScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public Player getPlayer() {
        return player;
    }
}
