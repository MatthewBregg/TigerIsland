package ui;

import org.junit.*;
import tigerisland.datalogger.LoggerFactory;
import tigerisland.datalogger.SQLiteLogger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static junit.framework.TestCase.assertFalse;

public class TestTest {

    private static SQLiteLogger logger = null;
    private static int ChallengeId = 1;
    private static char GameId = 'A';
    private static int match_id = 1;
    private static String url = null;
    private static AtomicLong currTime;

    @BeforeClass
    public static void setUpStatic() {
       url =  "jdbc:sqlite::memory:";
       LoggerFactory.setDataBaseUrl(url);
       logger = LoggerFactory.getSQLLogger(GameId,ChallengeId,match_id);
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
    public void writeRawRequest() throws Exception {

        // Arrange
        logger = LoggerFactory.getSQLLogger(GameId,ChallengeId,match_id);
        currTime.incrementAndGet();
        int time = currTime.intValue();
        logger.writeRawRequest(time, "foo");

        // Act
        Map<Integer, Map<Integer, Integer>> scores = new HashMap<>();
        Connection connection = LoggerFactory.getDbConnection();
        String queryScoreString = "SELECT * FROM raw_requests;";
        try {
            // raw_requests
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(queryScoreString);
            int i = 5;
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }

    }
}
