package tigerisland.datalogger;

import org.junit.*;
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

    @BeforeClass
    public static void setUpStatic() {
       url =  "jdbc:sqlite::memory:";

        LoggerFactory.setDataBaseUrl(url);
        Map<Integer, String>  playersIdToUsername = new HashMap<>();
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
        Assert.assertEquals(player1.getId().getId(), matchRow.getP1_id());
        Assert.assertEquals(player2.getId().getId(), matchRow.getP2_id());
    }
}
