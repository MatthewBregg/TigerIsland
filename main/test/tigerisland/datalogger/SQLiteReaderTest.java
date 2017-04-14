package tigerisland.datalogger;

import org.junit.*;
import tigerisland.board.Location;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class SQLiteReaderTest {

    private static SQLiteLogger logger = null;
    private static SQLiteReader reader = null;
    private static int ChallengeId = 1;
    private static char GameId = 'A';
    private static int match_id = 1;
    private static String url = null;
    private static AtomicLong currTime;
    private static Map<Integer, String> playersIdToUsername;

    @BeforeClass
    public static void setUpStatic() {
       url =  "jdbc:sqlite::memory:";

        LoggerFactory.setDataBaseUrl(url);
        playersIdToUsername = new HashMap<>();
       logger = LoggerFactory.getSQLLogger(GameId,ChallengeId,match_id, playersIdToUsername);
       Connection connection = LoggerFactory.getDbConnection();
       reader = new SQLiteReader(connection);

       Assert.assertFalse(logger.hasErrored());
       currTime = new AtomicLong(1);
    }

    @Before
    public void setUp() {

        logger.clearError();
        logger.nextTurn();
        LoggerFactory.clearTables();
        assertFalse(logger.hasErrored());
    }

    @After
    public void teardown() {
        assertFalse(logger.hasErrored());
        LoggerFactory.clearTables();
        logger.nextTurn();
    }

    @Test
    public void test_ShouldRetrievePlayersChallengesScoresFromDB() throws Exception {

       // Arrange
        int challengeId = 1;
        Player player1 = new Player( new PlayerID());
        int player1Score = 120;
        Player player2 = new Player( new PlayerID());
        int player2Score = 200;

        logger.setPlayerScore(challengeId, player1.getId(), player1Score);
        logger.setPlayerScore(challengeId, player2.getId(), player2Score);

       // Act
        Map<Integer, Map<Integer, Integer>> scores = reader.getPlayersScoresPerChallenge();

       // Assert
       assertEquals(1, scores.size());
       assertEquals(2, scores.get(challengeId).size());
    }

    @Test
    public void test_ShouldRetrieveAllMatchesFromDB() {

        // Arrange
        Player player1 = new Player( new PlayerID());
        Player player2 = new Player( new PlayerID());
        logger.writeGameEnded(player1.getId(), player2.getId(), "");

        // Act
        List<MatchRow> matches =  reader.getAllMatches();

        // Assert
        Assert.assertEquals(1, matches.size());

        MatchRow matchRow = matches.get(0);
        Assert.assertEquals( String.valueOf(player1.getId().getId()), matchRow.getP1_id());
        Assert.assertEquals( String.valueOf(player2.getId().getId()), matchRow.getP2_id());
    }

    @Test
    public void test_ShouldBeAbleToReadCurrentChallenge() {

        // Arrange
        Player player1 = new Player( new PlayerID());
        Player player2 = new Player( new PlayerID());
        logger.writeGameEnded(player1.getId(), player2.getId(), "");

        char game_id = 'B';
        int challenge2 = Integer.MAX_VALUE;
        logger.newGame(game_id, challenge2);
        logger.writeGameEnded(player1.getId(), player2.getId(), "");

        // Act
        int currentChallengeBeenPlayed = reader.getCurrentChallengeBeenPlayed();

        // Assert
        Assert.assertEquals(challenge2, currentChallengeBeenPlayed);
    }

    @Test
    public void test_ShouldReturnTeamTournamentScore() {

        // Arrange
        String userName = "A";
        PlayerID playerId = new PlayerID();
        playersIdToUsername.put(playerId.getId(), userName);
        int expectedScore = 5;
        logger.writeToTournamentScore(playerId, expectedScore);

        // Act
        int teamTournamentScore = reader.getTeamTournamentScore(userName);

        // Assert
        Assert.assertEquals(expectedScore , teamTournamentScore);
    }

    @Test
    public void test_ShouldReturnATeamScoreForAChallenge() {

        // Arrange
        String teamName = "TEAM_A";
        PlayerID playerId = new PlayerID();
        playersIdToUsername.put(playerId.getId(), teamName);

        int expectedScore = 10;
        char gameId = 'x';
        int challengeId = 5;

        logger.newGame(gameId, challengeId);
        logger.setPlayerScore(challengeId, playerId, expectedScore);

        // Act
        int teamTournamentScore = reader.getTeamScoreForChallenge(teamName, challengeId);

        // Assert
        Assert.assertEquals(expectedScore , teamTournamentScore);
    }

    @Test
    public void test_ShouldReturnTheCurrentMatchForAChallenge() {

        // Arrange
        String teamName = "TEAM_C";
        PlayerID player1Id = new PlayerID(); playersIdToUsername.put(player1Id.getId(), teamName);
        PlayerID player2Id = new PlayerID(); playersIdToUsername.put(player1Id.getId(), "TEAM_H");

        int expectedMatch = 1;
        char gameId = 'x';
        int challengeId = 5;

        logger.newGame(gameId, challengeId);
        logger.writeGameStarted(player1Id, player2Id);

        // Act
        int match = reader.getCurrentMatchForChallenge(challengeId);

        // Assert
        Assert.assertEquals(expectedMatch, match);
    }

    @Test
    public void test_ShouldScoreForMatch() {


        // Arrange
        String teamName = "TEAM_C";
        PlayerID player1Id = new PlayerID(); playersIdToUsername.put(player1Id.getId(), teamName);
        int moveId = 5;

        char gameId = 'x';
        int challengeId = 5;

        logger.newGame(gameId, challengeId);
        logger.writeToGameTurnScore(player1Id, moveId, 5);

        // Act
        int score = reader.getScoreForPlayerGame(challengeId, teamName, match_id, gameId);

        // Assert
        Assert.assertEquals(5, score);

    }

    @Test
    public void test_ShouldReturnPlayerOpponent() {

        // Arrange
        String teamName = "TEAM_C";
        PlayerID player1Id = new PlayerID(); playersIdToUsername.put(player1Id.getId(), teamName);

        String expectedOpponent = "TEAM_Z";
        PlayerID player2Id = new PlayerID(); playersIdToUsername.put(player2Id.getId(), expectedOpponent);

        char gameId = 'X';
        int challengeId = 5;
        logger.newGame(gameId, challengeId);
        logger.writeGameStarted(player1Id , player2Id);

        // Act
        String opponent = reader.getOpponent(teamName, challengeId, match_id);

        // Assert
        Assert.assertEquals(expectedOpponent, opponent);
    }

    @Test
    public void test_ShouldReturnTotorosPlayedForAMatch() {

        // Arrange
        String teamName = "TEAM_C";
        PlayerID player1Id = new PlayerID(); playersIdToUsername.put(player1Id.getId(), teamName);
        int moveId = 5;

        char gameId = 'X';
        int challengeId = 5;

        logger.newGame(gameId, challengeId);
        logger.writePlacedTotoroMove(player1Id, new Location(0,0,0));
        logger.nextTurn();
        logger.writePlacedTotoroMove(player1Id, new Location(0,0,0));

        // Act
        int totoro = reader.getTotoroForGame(challengeId, teamName, match_id, gameId);

        // Assert
        Assert.assertEquals(2, totoro);
    }

    @Test
    public void test_ShouldReturnTigersForMatch() {

        // Arrange
        String teamName = "TEAM_C";
        PlayerID player1Id = new PlayerID(); playersIdToUsername.put(player1Id.getId(), teamName);
        int moveId = 5;

        char gameId = 'X';
        int challengeId = 5;

        logger.newGame(gameId, challengeId);
        logger.writePlacedTigerMove(player1Id, new Location(0,0,0));
        logger.nextTurn();
        logger.writePlacedTigerMove(player1Id, new Location(0,0,0));

        // Act
        int totoro = reader.getTigerForGame(challengeId, teamName, match_id, gameId);

        // Assert
        Assert.assertEquals(2, totoro);
    }

}
