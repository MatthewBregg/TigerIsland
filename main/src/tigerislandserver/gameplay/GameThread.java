package tigerislandserver.gameplay;

/**
 * Created by christinemoore on 3/23/17.
 */

import tigerisland.tile.*;
import tigerisland.player.*;
import tigerisland.score.*;

import java.util.ArrayList;

public class GameThread extends Thread{
    private ArrayList<Tile> gameTiles;
    private ArrayList<Player> playersInGame;
    private int activePlayerIndex;
    private int gameID;
    private ScoreManager scoreManager;

    // tournament class would pass in the seed and gameID
    public GameThread(Player player1, Player player2, ArrayList<Tile> tiles, int gameID){
        playersInGame.add(player1);
        playersInGame.add(player2);
        activePlayerIndex = 0;
        gameTiles = tiles;
        this.gameID = gameID;
        scoreManager = new ScoreManager();
    }

    public void sendStartGameMessage(){
        // to send starting game info that dave is probably going to make us send
        // needs to utilize the commands int he connection classes
        //player1
        //player2
    }

    public void sendEndGameMessage(){

    }





    public void run(){
        // tbd
    }
}
