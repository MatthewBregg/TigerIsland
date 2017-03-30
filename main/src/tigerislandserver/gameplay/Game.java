package tigerislandserver.gameplay;

/**
 * Created by christinemoore on 3/23/17.
 */

import tigerisland.tile.*;
import tigerisland.player.*;
import tigerisland.score.*;

import java.util.ArrayList;

public class Game extends Thread{
    private ArrayList<Tile> gameTiles;
    private Player player1;
    private Player player2;
    private int gameID;
    private ScoreManager scoreManager;

    // tournament class would pass in the seed and gameID
    public Game(Player player1, Player player2, ArrayList<Tile> tiles, int gameID){
        gameTiles = tiles;
        this.gameID = gameID;
        this.player1 = player1;
        this.player2 = player2;
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
