package ui;

import org.junit.*;
import tigerisland.datalogger.*;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class ScoreBoardMainTest {

    private static GenerateScoreBoard scoreBoardGenerator;
    private static SQLiteLogger logger = null;
    private static DataReader sqliteDataReader = null;
    private static int ChallengeId = 1;
    private static char GameId = 'A';
    private static int match_id = 1;
    private static String url = null;
    private static AtomicLong currTime;

    @BeforeClass
    public static void setUpStatic() {
       url =  "jdbc:sqlite:tigersssss.db";
       LoggerFactory.setDataBaseUrl(url);

       logger = LoggerFactory.getSQLLogger(GameId, ChallengeId, match_id);
       sqliteDataReader = new SQLiteReader(LoggerFactory.getDbConnection());
       scoreBoardGenerator = new GenerateScoreBoard(sqliteDataReader);

       Assert.assertFalse(logger.hasErrored());
       currTime = new AtomicLong(1);
    }

    @Before
    public void setUp() {
        logger.clearError();
        logger.nextTurn();
        assertFalse(logger.hasErrored());
    }

//    @After
//    public void teardown() {
//        assertFalse(logger.hasErrored());
//        LoggerFactory.clearTables();
//        logger.nextTurn();
//    }

    @Test
    public void test_ShouldWriteToTigersSQLiteDB() throws Exception {

        // Arrange
        Player player1 = new Player( new PlayerID() );
        Player player2 = new Player( new PlayerID() );
        logger.writeGameEnded(player1.getId(), player2.getId(), "");

        List<MatchRow> matches = sqliteDataReader.getAllMatches();
        assertEquals(1, matches.size());
    }

    @Test
    public void test_ShouldReadFromTigersSQLiteDB() throws Exception {

        // Arrange
        List<MatchRow> matches = sqliteDataReader.getAllMatches();
        assertEquals(1, matches.size());
    }
}
