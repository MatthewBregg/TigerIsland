package tigerisland.score;

/**
 * Created by christinemoore on 3/21/17.
 */

import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.hex.Hex;

public class ScoreManager{
    private Score p1Score;
    private Score p2Score;

    public ScoreManager(Player player1, Player player2){
        p1Score = new Score(player1);
        p2Score = new Score(player2);
    }

    public Score getPlayerScore(PlayerID pID){
        Score playerScoreToReturn;

        if (pID == p1Score.getPlayer().getId()){
            playerScoreToReturn = p1Score;
        }
        else if (pID == p2Score.getPlayer().getId()){
           playerScoreToReturn = p2Score;
        }
        else{
            throw new RuntimeException("invalid playerID");
        }
        return playerScoreToReturn;
    }

    public PlayerID getPlayer1ID(){
        return p1Score.getPlayerId();
    }

    public PlayerID getPlayer2ID(){
        return p2Score.getPlayerId();
    }

    public int getPlayer1Score(){
        return p1Score.getScore();
    }

    public int getPlayer2Score(){
        return p2Score.getScore();
    }

    public void resetPlayerScores(){
        p1Score.setScore(0);
        p2Score.setScore(0);
    }

    public void addBuildNewSettlementScore(PlayerID pID){
        Score score = getPlayerScore(pID);
        score.addPointsToScore(1);
    }

    /* a lot of different ways we
    can go about this, so i made a lot of
    different methods to be integrated*/


    // we would use this method if we were passed a hex each time
    public void addMeeplePlacementScoreDueToExpansion(PlayerID pID, int currentLevel){
        Score score = getPlayerScore(pID);

        score.addPointsToScore(currentLevel*currentLevel);
    }


    // we would use this method if we were passed a hex each time
    public void addMeeplePlacementScoreDueToExpansion(PlayerID pID, Hex currentHex){
        Score score = getPlayerScore(pID);
        int level = currentHex.getLevel();

        score.addPointsToScore(level*level);
    }

    public void addTotoroScore(PlayerID pID){
        Score score = getPlayerScore(pID);
        score.addPointsToScore(200);
    }

    public void addTigerScore(PlayerID pID){
        Score score = getPlayerScore(pID);
        score.addPointsToScore(75);
    }


    public void addScore(PlayerID id, int hexLevel) {
        Score score = getPlayerScore(id);
        score.addPointsToScore(hexLevel);
    }
}