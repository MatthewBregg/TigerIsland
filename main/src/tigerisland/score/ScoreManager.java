package tigerisland.score;

/**
 * Created by christinemoore on 3/21/17.
 */

import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.hex.Hex;
import java.util.*;

public class ScoreManager{
    Map<PlayerID, Integer> playerScores;

    public ScoreManager(){
        playerScores = new HashMap();
    }

    public void addNewPlayer(PlayerID pID){
        playerScores.put(pID, 0);
    }

    public int getPlayerScore(PlayerID pID){
        return playerScores.get(pID);
    }

    public int getTotalNumberOfPlayers(){
        return playerScores.size();
    }

    public void resetPlayerScore(PlayerID pID){
        playerScores.put(pID, 0);
    }

    public void buildOnNewHex(PlayerID pID, int hexLevel){
        int pointsToAdd = hexLevel * hexLevel;
        int existingPoints = playerScores.get(pID);
        existingPoints += pointsToAdd;
        playerScores.put(pID, existingPoints);
    }

    public void addTotoroScore(PlayerID pID){
        int existingPoints = playerScores.get(pID);
        existingPoints += 200;
        playerScores.put(pID, existingPoints);
    }

    public void addTigerScore(PlayerID pID){
        int existingPoints = playerScores.get(pID);
        existingPoints += 75;
        playerScores.put(pID, existingPoints);
    }


    public void addScore(PlayerID id, int hexLevel) {
        Score score = getPlayerScore(id);
        score.addPointsToScore(hexLevel);
    }
}