package tigerislandserver.gameplay;

/**
 * Created by christinemoore on 3/23/17.
 */

import tigerisland.game.GameManager;
import tigerisland.tile.*;
import tigerisland.score.*;
import tigerislandserver.gameplay.identifiers.GameID;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;

public class GameThread extends Thread{
    private ArrayList<Tile> gameTiles;
    private ArrayList<TournamentPlayer> playersInGame;
    private int activePlayerIndex;
    private char gameID;
    private ScoreManager scoreManager;
    private int numberOfMoves;
    private GameManager gameManager;

    // tournament class would pass in the seed and gameID
    public GameThread(ArrayList<TournamentPlayer> players, ArrayList<Tile> tiles, char gameLetter){
        playersInGame = players;
        activePlayerIndex = 0;
        gameTiles = tiles;
        gameID = gameLetter;
        scoreManager = new ScoreManager();
        gameManager = new GameManager();
    }

    public void sendStartGameMessage(){
        // to send starting game info that dave is probably going to make us send
        // needs to utilize the commands int he connection classes
        //player1
        //player2
    }

    public int getActivePlayerIndex(){
        return activePlayerIndex;
    }

    public ArrayList<Tile> getGameTiles(){
        return gameTiles;
    }



    public boolean makeBuildMove(){

        return false;
    }


    public void sendEndGameMessage(){

    }

    public long getGameID(){
        return gameID;
    }




    public void run(){
        // TODO: call Game protocol
        // tbd
    }
}
