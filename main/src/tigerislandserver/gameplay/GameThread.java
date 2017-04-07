package tigerislandserver.gameplay;

import tigerisland.game.GameManager;
import tigerisland.player.PlayerID;
import tigerisland.player.Player;
import tigerisland.tile.*;
import tigerisland.score.*;
import tigerislandserver.adapter.OutputAdapter;
import tigerislandserver.gameplay.identifiers.GameID;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;

import static tigerislandserver.gameplay.TournamentScoreboard.scoreManager;

public class GameThread extends Thread{
    private ArrayList<Tile> gameTiles;
    private ArrayList<TournamentPlayer> playersInGame;
    private int activePlayerIndex;
    private char gameID;
    private TournamentScoreboard scoreboard;
    private boolean gameNotEnded;
    private GameManager gameManager;

    public GameThread(ArrayList<TournamentPlayer> players, ArrayList<Tile> tiles, char gameLetter, TournamentScoreboard scoreboard){
        if (players.size() != 2)
        {
            throw new IllegalArgumentException("Exactly two players required");
        }

        playersInGame = players;
        activePlayerIndex = 0;
        gameTiles = tiles;
        gameID = gameLetter;

        this.scoreboard=scoreboard;

        gameNotEnded = true;

        ArrayList<Player> gamePlayers=new ArrayList<Player>();

        for(TournamentPlayer tp: players)
        {
            gamePlayers.add(new Player(tp.getID()));
        }

        gameManager = new GameManager(gamePlayers);
    }

    public ScoreManager getScoreManager(){
        return gameManager.getScoreManager();
    }

    public int getPlayer1FinalScore(){
        TournamentPlayer player1 = playersInGame.get(0);
        PlayerID pID = player1.getPlayerID();

        return gameManager.getScoreManager().getPlayerScore(pID);
    }

    public int getPlayer2FinalScore(){
        TournamentPlayer player2 = playersInGame.get(1);
        PlayerID pID = player2.getPlayerID();

        return gameManager.getScoreManager().getPlayerScore(pID);
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
        sendStartGameMessage();

        //Total Moves between both players that have happened
        // not turn number relative to a single player.
        int moveNumber = 0;

        while(gameNotEnded)
        {
            int playerTurnNumber = moveNumber/2;
            Tile tile = gameTiles.get(moveNumber);

            playersInGame.get(activePlayerIndex).requestMove(this, gameID, playerTurnNumber, tile);

            moveNumber++;
            activePlayerIndex = (activePlayerIndex + 1) % playersInGame.size();
        }

        sendEndGameMessage();
    }

    public void timeout(TournamentPlayer tournamentPlayer)
    {
        for(TournamentPlayer player: playersInGame)
        {
            //TODO
        }
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

    public ArrayList<TournamentPlayer> getPlayersInGame()
    {
        return playersInGame;
    }
}
