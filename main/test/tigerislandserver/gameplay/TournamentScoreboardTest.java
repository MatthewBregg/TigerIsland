package tigerislandserver.gameplay;

import org.junit.*;
import tigerisland.TestLogger;
import tigerisland.datalogger.ConsoleLogger;
import tigerisland.datalogger.DataLogger;
import tigerisland.datalogger.SQLiteLogger;
import tigerisland.game.GameManager;
import tigerisland.player.PlayerID;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerislandserver.server.TournamentPlayer;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by christinemoore on 4/9/17.
 */
public class TournamentScoreboardTest {
    public static ArrayList<TournamentPlayer> tourneyPlayers;
    public static Challenge tourneyRepresentation;
    public static Map<PlayerID, Integer> tournamentPlayerScores;
    public static TournamentScoreboard tournamentScoreboard;
    public static DataLogger dataLogger;
    public static ArrayList<Player> playerList;
    public static GameManager gameManager;

    @BeforeClass
    public static void suiteSetUp() throws Exception {
        tourneyPlayers = new ArrayList<>();
        dataLogger = new TestLogger();
        playerList = new ArrayList<>();

        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player(20, 2, 2, new PlayerID() );
        Player standardPlayer = new Player();
        Player player4 = new Player(20, 3, 1, new PlayerID());
        Player player5 = new Player(10, 3, 2, new PlayerID());



        playerList.add(player1);
        playerList.add(player2);
        //playerList.add(player3);
        //playerList.add(player4);
        //playerList.add(player5);

        // instantiate the challenge object
        tourneyRepresentation = new Challenge(tourneyPlayers,0);

        tournamentScoreboard = new TournamentScoreboard();
        tourneyRepresentation = new Challenge(tourneyPlayers,0);
        tournamentPlayerScores = new HashMap<>();
        gameManager = new GameManager(playerList, new TestLogger());

    }

    @Before
    public void resetScores(){
        tournamentScoreboard.resetPlayerScore(playerList.get(0).getId());
        tournamentScoreboard.resetPlayerScore(playerList.get(1).getId());
    }

    @Test
    public void makeSurePlayerScoresAreZeroAtStart(){
        boolean allZeros = true;
        // loop through the 10 players in the playres array list first

        PlayerID pID = playerList.get(0).getId();
        int scorePlayer1 = tournamentScoreboard.getPlayerScore(pID);

        PlayerID pID2 = playerList.get(1).getId();
        int scorePlayer2 = tournamentScoreboard.getPlayerScore(pID2);

        if (scorePlayer1 != 0){
            allZeros = false;
        }
        if (scorePlayer2 != 0){
            allZeros = false;
        }

        Assert.assertTrue(allZeros);
    }

    @Test
    public void add50PointsToPlayer1(){
        PlayerID pID = playerList.get(0).getId();

        tournamentScoreboard.addScore(pID, 50);
        int score = tournamentScoreboard.getPlayerScore(pID);

        Assert.assertTrue(score == 50);
    }

    //@Test
    //public void TournamentScoreboardData

    @Test
    public void playerWonWithValidHighScore(){
        // if they win with a valid score they get 50 points and opponent loses 10 points

        Player player1 = playerList.get(0);
        Player player2 = playerList.get(1);

        // first player
        PlayerID pID = player1.getId();
        // second player
        PlayerID pIDPlayer2 = player2.getId();

        tournamentScoreboard.addNewPlayer(pID);
        tournamentScoreboard.addNewPlayer(pIDPlayer2);

        // need to set those player scores to compare the highest score number
        ScoreManager scoreManager = gameManager.getScoreManager();

        TournamentScoreboardData player1Data = new TournamentScoreboardData(player1, 50);
        TournamentScoreboardData player2Data = new TournamentScoreboardData(player2, 10);

        ArrayList<TournamentScoreboardData> playerData = new ArrayList<>();
        playerData.add(player1Data);
        playerData.add(player2Data);

        boolean success = tournamentScoreboard.updateTournamentScoresForValidWin(playerData);

        Assert.assertTrue(success);
        boolean result = false;
        if (success){
            if((tournamentScoreboard.getPlayerScore(pID) == 50) && (tournamentScoreboard.getPlayerScore(pIDPlayer2) == -5)){
                result = true;
            }
        }

        Assert.assertTrue(result);
    }

    @Test
    public void playerWonWithTrueTie(){
        // if they win with a valid score they get 50 points and opponent loses 10 points

        Player player1 = playerList.get(0);
        Player player2 = playerList.get(1);

        // first player
        PlayerID pID = player1.getId();
        // second player
        PlayerID pIDPlayer2 = player2.getId();

        tournamentScoreboard.addNewPlayer(pID);
        tournamentScoreboard.addNewPlayer(pIDPlayer2);

        // need to set those player scores to compare the highest score number
        ScoreManager scoreManager = gameManager.getScoreManager();

        TournamentScoreboardData player1Data = new TournamentScoreboardData(player1, 10);
        TournamentScoreboardData player2Data = new TournamentScoreboardData(player2, 10);

        ArrayList<TournamentScoreboardData> playerData = new ArrayList<>();
        playerData.add(player1Data);
        playerData.add(player2Data);

        boolean success = tournamentScoreboard.updateTournamentScoresForValidWin(playerData);
        
        Assert.assertTrue(success);
        boolean result = false;
        if (success){
            if((tournamentScoreboard.getPlayerScore(pID) == 10) && (tournamentScoreboard.getPlayerScore(pIDPlayer2) == 10)){
                result = true;
            }
        }

        Assert.assertTrue(result);
    }

    @Test
    public void playerWonTieBreakerWithMoreTotoros(){
        // if they win with a valid score they get 50 points and opponent loses 10 points

        Player player3 = new Player(20, 2, 2, new PlayerID() );
        Player standardPlayer = new Player();

        playerList.set(0, player3);

        playerList.set(1, standardPlayer);

        // first player
        PlayerID pID = player3.getId();
        // second player
        PlayerID pIDPlayer2 = standardPlayer.getId();

        tournamentScoreboard.addNewPlayer(pID);
        tournamentScoreboard.addNewPlayer(pIDPlayer2);

        TournamentScoreboardData player1Data = new TournamentScoreboardData(player3, 10);
        TournamentScoreboardData player2Data = new TournamentScoreboardData(standardPlayer, 10);

        ArrayList<TournamentScoreboardData> playerData = new ArrayList<>();
        playerData.add(player1Data);
        playerData.add(player2Data);

        boolean success = tournamentScoreboard.updateTournamentScoresForValidWin(playerData);

        boolean result = false;
        if (success){
            if((tournamentScoreboard.getPlayerScore(pID) == 50) && (tournamentScoreboard.getPlayerScore(pIDPlayer2) == -2)){
                result = true;
            }
        }

        Assert.assertTrue(result);
    }

    @Test
    public void playerWonTieBreakerWithMoreTigers(){
        // if they win with a valid score they get 50 points and opponent loses 10 points

        Player player3 = new Player(20, 3, 1, new PlayerID() );
        Player standardPlayer = new Player();

        playerList.set(0, player3);

        playerList.set(1, standardPlayer);

        // first player
        PlayerID pID = player3.getId();
        // second player
        PlayerID pIDPlayer2 = standardPlayer.getId();

        tournamentScoreboard.addNewPlayer(pID);
        tournamentScoreboard.addNewPlayer(pIDPlayer2);

        TournamentScoreboardData player1Data = new TournamentScoreboardData(player3, 10);
        TournamentScoreboardData player2Data = new TournamentScoreboardData(standardPlayer, 10);

        ArrayList<TournamentScoreboardData> playerData = new ArrayList<>();
        playerData.add(player1Data);
        playerData.add(player2Data);

        boolean success = tournamentScoreboard.updateTournamentScoresForValidWin(playerData);

        boolean result = false;
        if (success){
            if((tournamentScoreboard.getPlayerScore(pID) == 50) && (tournamentScoreboard.getPlayerScore(pIDPlayer2) == -2)){
                result = true;
            }
        }

        Assert.assertTrue(result);
    }

    @Test
    public void playerWonTieBreakerWithMoreVillagers(){
        // if they win with a valid score they get 50 points and opponent loses 10 points

        Player player3 = new Player(10, 3, 2, new PlayerID() );
        Player standardPlayer = new Player();

        playerList.set(0, player3);

        playerList.set(1, standardPlayer);

        // first player
        PlayerID pID = player3.getId();
        // second player
        PlayerID pIDPlayer2 = standardPlayer.getId();

        tournamentScoreboard.addNewPlayer(pID);
        tournamentScoreboard.addNewPlayer(pIDPlayer2);

        TournamentScoreboardData player1Data = new TournamentScoreboardData(player3, 10);
        TournamentScoreboardData player2Data = new TournamentScoreboardData(standardPlayer, 10);

        ArrayList<TournamentScoreboardData> playerData = new ArrayList<>();
        playerData.add(player1Data);
        playerData.add(player2Data);

        boolean success = tournamentScoreboard.updateTournamentScoresForValidWin(playerData);

        boolean result = false;
        if (success){
            if((tournamentScoreboard.getPlayerScore(pID) == 50) && (tournamentScoreboard.getPlayerScore(pIDPlayer2) == -2)){
                result = true;
            }
        }

        Assert.assertTrue(result);
    }

    @Test
    public void scoresCanCompound(){
        Player player3 = new Player(10, 3, 2, new PlayerID() );
        Player standardPlayer = new Player();

        playerList.set(0, player3);

        playerList.set(1, standardPlayer);

        // first player
        PlayerID pID = player3.getId();
        // second player
        PlayerID pIDPlayer2 = standardPlayer.getId();

        tournamentScoreboard.addNewPlayer(pID);
        tournamentScoreboard.addNewPlayer(pIDPlayer2);

        TournamentScoreboardData player1Data = new TournamentScoreboardData(player3, 10);
        TournamentScoreboardData player2Data = new TournamentScoreboardData(standardPlayer, 10);

        ArrayList<TournamentScoreboardData> playerData = new ArrayList<>();
        playerData.add(player1Data);
        playerData.add(player2Data);

        boolean success = tournamentScoreboard.updateTournamentScoresForValidWin(playerData);

        boolean result = false;
        if (success){
            if((tournamentScoreboard.getPlayerScore(pID) == 50) && (tournamentScoreboard.getPlayerScore(pIDPlayer2) == -2)){
                result = true;
            }
        }

        Assert.assertTrue(result);
    }
}
