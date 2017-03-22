package tigerisland.score;

/**
 * Created by christinemoore on 3/21/17.
 */

public class Score{
    private int playerScore;
    Player player;


    public Score(Player player){
        this.player = player;
        playerScore = 0;
    }

    public void setScore(int newScore){
        playerScore = newScore;
    }

    public void addPointsToScore(int pointsToAdd){
        playerScore += pointsToAdd;
    }

    public int getScore(){
        return playerScore;
    }

    public int getPlayer(){
        return player;
    }

}