package tigerisland.score;

/**
 * Created by christinemoore on 3/21/17.
 */

import tigerisland.player.PlayerID;

import java.util.HashMap;
import java.util.Map;


public class ScoreManager{
    Map<PlayerID, Integer> playerScores;

    public ScoreManager(){
        playerScores = new HashMap();
    }

    public void addNewPlayer(PlayerID pID){
        playerScores.put(pID, 0);
    }

    public int getPlayerScore(PlayerID pID){
        if (playerScores.get(pID)!=null)
            return playerScores.get(pID);

        return 0;
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


    public void addScore(PlayerID id, int valueToAdd) {

        int score = this.getPlayerScore(id);

        if (score != 0) {
            score += valueToAdd;
            playerScores.put(id, score);
        }
        else
            playerScores.put(id, valueToAdd);
    }
}