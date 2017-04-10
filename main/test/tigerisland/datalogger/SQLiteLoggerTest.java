package tigerisland.datalogger;

import org.junit.*;
import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import static org.junit.Assert.*;

public class SQLiteLoggerTest {

    private static SQLiteLogger logger = null;
    private static int ChallengeId = 1;
    private static int GameId = 1;
    private static int match_id = 1;
    private static String url = null;

    @BeforeClass
    public static void setUpStatic() {
       url =  "jdbc:sqlite::memory:";
       logger = new SQLiteLogger(ChallengeId,GameId,match_id,url);
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

       logger.createTables();
    }
    @Test
    public void writeRawRequest() throws Exception {
        logger.writeRawRequest(0,"foo!");
    }

    @Test
    public void writePlacedTotoro() throws Exception {
        logger.writePlacedTotoroMove(new PlayerID(), new Location(0,0,0));
    }

    @Test
    public void writeFoundedSettlement() throws Exception {
        logger.writeFoundedSettlementMove(new PlayerID(), new Location(0,0,0));
    }

    @Test
    public void writeExpandedSettlement() throws Exception {
        logger.writeExpandedSettlementMove(new PlayerID(), new Location(0,0,0), "FooTerrain");
    }

    @Test
    public void writePlacedTiger() throws Exception {
        logger.writePlacedTigerMove(new PlayerID(), new Location(0,0,0));
    }

    @Test
    public void writePlacedTile() throws Exception {
        logger.writePlacedTileMove(new PlayerID(), new Location(0,0,0), new Orientation(0), "foo");
    }

    @Test
    public void writeMovedWasInvalid() throws Exception {
        logger.writeInvalidMoveAttempted(new PlayerID(), "boo!");
    }

    @Test
    public void writeGameEnded() throws Exception {
        logger.writeGameEnded(new PlayerID(), new PlayerID(),"fooadf");
    }

    @Test
    public void writeGameStarted() throws Exception {
        logger.writeGameStarted(new PlayerID(), new PlayerID());
    }

    @Test
    public void setPlayerScore() throws Exception {
        logger.setPlayerScore(0, new PlayerID(),120);
    }
}