package tigerislandserver.gameplay;

import tigerisland.game.GameManager;
import tigerisland.player.Player;
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
    private boolean gameNotEnded;
    private GameManager gameManager;

    public GameThread(ArrayList<TournamentPlayer> players, ArrayList<Tile> tiles, char gameLetter){
        if (players.size() != 2)
        {
            throw new IllegalArgumentException("Exactly two players required");
        }

        playersInGame = players;
        activePlayerIndex = 0;
        gameTiles = tiles;
        gameID = gameLetter;
        scoreManager = new ScoreManager();

        gameNotEnded = true;

        ArrayList<Player> gamePlayers=new ArrayList<Player>();

        for(TournamentPlayer tp: players)
        {
            gamePlayers.add(new Player(tp.getID()));
        }

        gameManager = new GameManager(gamePlayers);
    }

    private void sendStartGameMessage(){
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
        sendStartGameMessage();

        //Total Moves between both players that have happened
        // not turn number relative to a single player.
        int moveNumber = 0;

        while(gameNotEnded)
        {
            int playerTurnNumber = ++moveNumber/2;

            playersInGame.get(activePlayerIndex++).requestMove(this, gameID, moveNumber++/2, gameTiles.get(moveNumber));
        }

        sendEndGameMessage();
    }

    public void timeout(TournamentPlayer tournamentPlayer)
    {
        //TODO
    }

    public void unableToBuild(TournamentPlayer tournamentPlayer)
    {
        //TODO
    }

    public void invalidTilePlacement(TournamentPlayer tournamentPlayer)
    {
        //TODO
    }

    public void invalidBuild(TournamentPlayer tournamentPlayer)
    {
        //TODO
    }

    public void successfulMove(String message)
    {
        //TODO
    }

    public GameManager getGameManager()
    {
        return gameManager;
    }
}
