package tigerisland.datalogger;

import org.junit.*;
import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class SQLiteLoggerTest {
    private static SQLiteLogger logger = null;
    private static int ChallengeId = 1;
    private static int GameId = 1;
    private static String url = null;
    private static Connection connection;

    @BeforeClass
    public static void setUpStatic() {
       url =  "jdbc:sqlite::memory:";
       logger = new SQLiteLogger(ChallengeId,GameId,url);
       connection = logger.getConnection();
       createTables();
       Assert.assertFalse(logger.hasErrored());
    }

    @Before
    public void setUp() {
        logger.clearError();
        logger.nextTurn();
    }

    @After
    public void teardown() {
        assertFalse(logger.hasErrored());
    }

    private static void createTables() {

       String[] queries = new String[]{
               "CREATE TABLE IF NOT EXISTS matches (challenge_id integer not null, game_id integer not null, p1_id integer not null, p2_id integer not null, status string, primary key(challenge_id, game_id) );",
               "CREATE TABLE IF NOT EXISTS tiles_placed (challenge_id integer not null, game_id integer not null, turn_number integer not null, p_id integer not null, loc_x integer not null, loc_y integer not null, loc_z integer not null, orientation integer not null, tile text not null, primary key(challenge_id, game_id, turn_number) );",
               "CREATE TABLE IF NOT EXISTS build_action (challenge_id integer not null, game_id integer not null, turn_number integer not null, p_id integer not null, loc_x integer not null, loc_y integer not null, loc_z integer not null, move_description text not null, primary key(challenge_id, game_id, turn_number) );",
               "CREATE TABLE IF NOT EXISTS invalid_moves (challenge_id integer not null, game_id integer not null, turn_number integer not null, p_id integer not null, message string not null, primary key(challenge_id, game_id, turn_number) );",
               "create table IF NOT EXISTS raw_requests ( time_stamp integer primary key, request text not null);"
       };

        try {
            for ( String query : queries ) {
                Statement stmt = connection.createStatement();
                stmt.execute(query);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void writeRawRequest() throws Exception {
        logger.writeRawRequest(0,"foo!");
    }

    @Test
    public void writePlacedTotoro() throws Exception {
        logger.writePlacedTotoro(new PlayerID(), new Location(0,0,0));
    }

    @Test
    public void writeFoundedSettlement() throws Exception {
        logger.writeFoundedSettlement(new PlayerID(), new Location(0,0,0));
    }

    @Test
    public void writeExpandedSettlement() throws Exception {
        logger.writeExpandedSettlement(new PlayerID(), new Location(0,0,0), "FooTerrain");
    }

    @Test
    public void writePlacedTiger() throws Exception {
        logger.writePlacedTiger(new PlayerID(), new Location(0,0,0));
    }

    @Test
    public void writePlacedTile() throws Exception {
        logger.writePlacedTile(new PlayerID(), new Location(0,0,0), new Orientation(0), "foo");
    }

    @Test
    public void writeMovedWasInvalid() throws Exception {
        logger.writeInvalidMove(new PlayerID(), "boo!");
    }

    @Test
    public void writeGameEnded() throws Exception {
        logger.writeGameEnded(new PlayerID(), new PlayerID());
    }

    @Test
    public void writeGameStarted() throws Exception {
        logger.writeGameStarted(new PlayerID(), new PlayerID());
    }
}