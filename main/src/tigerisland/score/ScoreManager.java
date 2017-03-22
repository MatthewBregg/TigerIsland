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

    public void addBuildNewSettlementScore(PlayerID pID){
        Score score = getPlayerScore(pID);
        score.addPointsToScore(1);
    }

    /* a lot of different ways we
    can go about this, so i made a lot of
    different methods to be integrated

    NOTE
    settlement was set as string because there is no real settlement
    object to work on
     */
    public void addMeeplePlacementScoreDueToExpansion(PlayerID pID, String settlement ){
        // this one is if we were passed some kind of list of hexes expanded too
        Score score = getPlayerScore(pID);

        // need a way to determine hex in settlement and level of hex
        // assuming this is possible it would be something like this

        /*
        for (Hex h: settlement.getExpandedHexList()){
            score.addPointsToScore(h.level);
        }
        */
    }

    // we would use this method if we were passed a hex each time
    public void addMeeplePlacementScoreDueToExpansion(PlayerID pID, Hex currentHex){
        Score score = getPlayerScore(pID);
        score.addPointsToScore(currentHex.getLevel());
    }


    public void addTotoroScore(PlayerID pID){
        Score score = getPlayerScore(pID);
        score.addPointsToScore(200);
    }

    public void addTigerScore(PlayerID pID){
        Score score = getPlayerScore(pID);
        score.addPointsToScore(75);
    }


}