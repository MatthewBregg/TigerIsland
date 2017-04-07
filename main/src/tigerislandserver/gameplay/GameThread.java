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

    public GameThread(TournamentPlayer player1, TournamentPlayer player2, ArrayList<Tile> tiles, char gameLetter, TournamentScoreboard scoreboard){
        playersInGame = new ArrayList<TournamentPlayer>();
        playersInGame.add(player1);
        playersInGame.add(player2);

        activePlayerIndex = 0;
        gameTiles = tiles;
        gameID = gameLetter;

        this.scoreboard=scoreboard;

        gameNotEnded = true;

        ArrayList<Player> gamePlayers=new ArrayList<Player>();

        for(TournamentPlayer tp: playersInGame)
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
        TournamentPlayer p1 = playersInGame.get(0);
        TournamentPlayer p2 = playersInGame.get(1);
        ScoreManager sm =gameManager.getScoreManager();
        OutputAdapter.sendEndGameMessage(p1, p2, gameID, ""+sm.getPlayerScore(p1.getID()), ""+sm.getPlayerScore(p2.getID()));
        gameNotEnded=false;
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

            if(gameManager.isGameDone())
            {
                gameNotEnded = false;
            }
        }

        sendEndGameMessage();
    }

    public void timeout(TournamentPlayer tournamentPlayer)
    {
        //TODO The message for turn is already sent so the client when this is called.
        OutputAdapter.sendEndGameMessage(tournamentPlayer, otherPlayer(tournamentPlayer), gameID, "FORFEITED", "WIN");
        gameNotEnded=false;
    }

    public void unableToBuild(TournamentPlayer tournamentPlayer)
    {
        //TODO
        OutputAdapter.sendEndGameMessage(tournamentPlayer, otherPlayer(tournamentPlayer), gameID, "FORFEITED", "WIN");
        gameNotEnded=false;
    }

    public void invalidTilePlacement(TournamentPlayer tournamentPlayer)
    {
        //TODO
        OutputAdapter.sendEndGameMessage(tournamentPlayer, otherPlayer(tournamentPlayer), gameID, "FORFEITED", "WIN");
        gameNotEnded=false;
    }

    public void invalidBuild(TournamentPlayer tournamentPlayer)
    {
        //TODO
        OutputAdapter.sendEndGameMessage(tournamentPlayer, otherPlayer(tournamentPlayer), gameID, "FORFEITED", "WIN");
        gameNotEnded=false;
    }

    public GameManager getGameManager()
    {
        return gameManager;
    }

    public ArrayList<TournamentPlayer> getPlayersInGame()
    {
        return playersInGame;
    }

    private TournamentPlayer otherPlayer(TournamentPlayer tp)
    {
        return playersInGame.get((playersInGame.indexOf(tp)+1)%2);
    }
}
