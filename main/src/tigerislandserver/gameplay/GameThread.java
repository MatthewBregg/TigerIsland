package tigerislandserver.gameplay;

import tigerisland.datalogger.DataLogger;
import tigerisland.datalogger.LoggerFactory;
import tigerisland.game.GameManager;
import tigerisland.player.PlayerID;
import tigerisland.player.Player;
import tigerisland.tile.*;
import tigerisland.score.*;
import tigerislandserver.adapter.OutputAdapter;
import tigerislandserver.server.TournamentPlayer;

import java.util.ArrayList;

public class GameThread extends Thread{
    private DataLogger logger;
    private ArrayList<Tile> gameTiles;
    private ArrayList<TournamentPlayer> playersInGame;
    private int activePlayerIndex;
    private char gameID;
    private TournamentScoreboard scoreboard;
    private TournamentScoreboardData tourneyDataPlayer1;
    private TournamentScoreboardData tourneyDataPlayer2;
    private boolean gameNotEnded;
    private GameManager gameManager;

    public GameThread(TournamentPlayer player1, TournamentPlayer player2, ArrayList<Tile> tiles, char gameLetter, int roundNumber, TournamentScoreboard scoreboard){
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
        logger = LoggerFactory.getLogger(((gameLetter == 'A') ? 0 : 1),roundNumber);
        gameManager = new GameManager(gamePlayers, logger );
    }

    public ScoreManager getScoreManager(){
        return gameManager.getScoreManager();
    }

    public int getPlayerFinalScore(int playerIndex){
        TournamentPlayer player1 = playersInGame.get(playerIndex);
        PlayerID pID = player1.getID();

        return gameManager.getScoreManager().getPlayerScore(pID);
    }

    // to hold the data the tournament scoreboard needs
    // there is a new class taht has been made
    public ArrayList<TournamentScoreboardData> makeTournamentScoreboardDataList(){
        int player1Index = 0;
        int player2Index = 1;
        TournamentScoreboardData player1Data = makeTournamentScoreboardData(player1Index);
        TournamentScoreboardData player2Data = makeTournamentScoreboardData(player2Index);

        ArrayList<TournamentScoreboardData> playerData = new ArrayList<>();
        playerData.add(player1Data);
        playerData.add(player2Data);

        return playerData;
    }

    public TournamentScoreboardData makeTournamentScoreboardData(int playerIndex){
        Player player = getPlayerFromPID(playersInGame.get(playerIndex).getID());

        int finalScore = getPlayerFinalScore(playerIndex);

        TournamentScoreboardData data = new TournamentScoreboardData(player, finalScore);
        return data;
    }


    public Player getPlayerFromPID(PlayerID pID){
        Player player = gameManager.getPlayer(pID);
        return player;
    }

    public void sendStartGameMessage(){
        // to send starting game info that dave is probably going to make us send
        // needs to utilize the commands int he connection classes
        //player1
        //player2
        logger.writeGameStarted(playersInGame.get(0).getID(), playersInGame.get(1).getID());
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
        logger.writeGameStarted(playersInGame.get(0).getID(), playersInGame.get(1).getID());
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

            if (gameEndedWithValidWin()){
                ArrayList<TournamentScoreboardData> playerData = makeTournamentScoreboardDataList();
                scoreboard.updateTournamentScoresForValidWin(playerData);
                endGame();
            }

            moveNumber++;
            logger.nextTurn();
            activePlayerIndex = (activePlayerIndex + 1) % playersInGame.size();
        }


        sendEndGameMessage();
    }

    public boolean playerUsedAllOfTwoTiles(PlayerID pID){
        ArrayList<Player> players = gameManager.getPlayers();
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        Player player = new Player();

        if(player1.getId() == pID){
            player = player1;
        }
        else if(player2.getId() == pID){
            player = player2;
        }

        if((player.getTigerCount() == 0) && (player.getTotoroCount() == 0)){
            return true;
        }
        else if((player.getTigerCount() == 0) && (player.getVillagerCount() == 0)){
            return true;
        }
        else if((player.getTotoroCount() == 0) && (player.getVillagerCount() == 0)){
            return true;
        }

        return false;
    }

    public boolean gameEndedWithValidWin(){
        //there are two ways the game ends validly
        // Player has only one type of piece left
        // Player wins tie
        TournamentPlayer player = playersInGame.get(activePlayerIndex);
        PlayerID pID = player.getID();

        boolean usedAllOfTwo = playerUsedAllOfTwoTiles(pID);
        boolean allTilesDrawn = noMoreTilesAreLeftToPlace();

        return usedAllOfTwo || allTilesDrawn;
    }

    public boolean noMoreTilesAreLeftToPlace(){
        if(gameManager.getTilesDrawn() == 48){
            return true;
        }

        return false;
    }

    public ArrayList<TournamentPlayer> generatePlayerToReturnToScoreboard(TournamentPlayer player){
        ArrayList<TournamentPlayer> players = new ArrayList<>();

        TournamentPlayer firstPlayer = playersInGame.get(0);
        TournamentPlayer secondPlayer =playersInGame.get(1);


        if (player.getID() == firstPlayer.getID()){
            players.add(firstPlayer);
            players.add(secondPlayer);
        }
        else if(player.getID() == secondPlayer.getID()){
            players.add(secondPlayer);
            players.add(firstPlayer);
        }

        return players;
    }

    public boolean isGameDone()
    {
        return gameNotEnded;
    }

    public void endGame()
    {
        gameNotEnded = false;
    }

    // pass botht the players so you can immediately add in the score

    public void timeout(TournamentPlayer tournamentPlayer)
    {
        OutputAdapter.sendEndGameMessage(tournamentPlayer, otherPlayer(tournamentPlayer), gameID, "FORFEITED", "WIN");
   //     OutputAdapter.sendEndGameMessage(otherPlayer(tournamentPlayer), tournamentPlayer, gameID, "WIN", "FORFEITED");

        gameNotEnded=false;
        ArrayList<TournamentPlayer> players = generatePlayerToReturnToScoreboard(tournamentPlayer);

        scoreboard.playerTimedOut(players);
    }

    public void unableToBuild(TournamentPlayer tournamentPlayer)
    {
        OutputAdapter.sendEndGameMessage(tournamentPlayer, otherPlayer(tournamentPlayer), gameID, "FORFEITED", "WIN");
 //       OutputAdapter.sendEndGameMessage(otherPlayer(tournamentPlayer), tournamentPlayer, gameID, "WIN", "FORFEITED");

        gameNotEnded=false;
        ArrayList<TournamentPlayer> players = generatePlayerToReturnToScoreboard(tournamentPlayer);

        boolean didTheyPlaceTigerOrTotoro = gameManager.totoroOrTigerPlaced(tournamentPlayer.getID());

        if (didTheyPlaceTigerOrTotoro){
            scoreboard.playerWasUnableToBuildAndPlacedSpecialPiece(players);
        }
        else {
            scoreboard.playerWasUnableToBuildAndPlacedNoSpecialPieces(players);
        }
    }

    public void invalidTilePlacement(TournamentPlayer tournamentPlayer)
    {
        OutputAdapter.sendEndGameMessage(tournamentPlayer, otherPlayer(tournamentPlayer), gameID, "FORFEITED", "WIN");
   //     OutputAdapter.sendEndGameMessage(otherPlayer(tournamentPlayer), tournamentPlayer, gameID, "WIN", "FORFEITED");
        gameNotEnded=false;
        ArrayList<TournamentPlayer> players = generatePlayerToReturnToScoreboard(tournamentPlayer);

        scoreboard.playerPlacedInvalidTile(players);
    }

    public void invalidBuild(TournamentPlayer tournamentPlayer)
    {
        OutputAdapter.sendEndGameMessage(tournamentPlayer, otherPlayer(tournamentPlayer), gameID, "FORFEITED", "WIN");
 //       OutputAdapter.sendEndGameMessage(otherPlayer(tournamentPlayer), tournamentPlayer, gameID, "WIN", "FORFEITED");
        gameNotEnded=false;
        ArrayList<TournamentPlayer> players = generatePlayerToReturnToScoreboard(tournamentPlayer);
        scoreboard.playerMadeInvalidBuild(players);
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
    // what
}
