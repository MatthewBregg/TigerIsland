package tigerislandserver.gameplay;

/**
 * Created by christinemoore on 3/23/17.
 */

import tigerisland.tile.*;
import tigerisland.score.*;
import tigerislandserver.gameplay.identifiers.GameID;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;

public class GameThread extends Thread{
    private ArrayList<Tile> gameTiles;
    private ArrayList<TournamentPlayer> playersInGame;
    private int activePlayerIndex;
    private long gameID;
    private ScoreManager scoreManager;
    private int numberOfMoves;

    // tournament class would pass in the seed and gameID
    public GameThread(ArrayList<TournamentPlayer> players, ArrayList<Tile> tiles){
        playersInGame = players;
        activePlayerIndex = 0;
        gameTiles = tiles;
        gameID = GameID.getID();
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

    public long getGameID(){
        return gameID;
    }




    public void run(){
        // TODO: call Game protocol
        // tbd
    }
}
