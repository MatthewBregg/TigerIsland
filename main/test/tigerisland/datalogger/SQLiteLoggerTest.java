package tigerisland.datalogger;

import org.junit.*;
import tigerisland.board.Location;
import tigerisland.player.PlayerID;
import tigerisland.tile.Orientation;

import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

public class SQLiteLoggerTest {

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
        currTime.incrementAndGet();
        int time = currTime.intValue();
        logger.writeRawRequest(time,"foo");
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

    class ConcurrentWriteTester implements Runnable {

        private int i = 0;

        ConcurrentWriteTester(int i) {
            this.i = i;
        }

        @Override
        public void run() {
                logger.writeRawRequest(this.i, "aldskfj;lsadkjf");
        }
    };

    @Test
    public void runWritesInParallel() throws Exception {

        for (int i = 0; i != 100; ++i ) {
            new Thread(new ConcurrentWriteTester(i)).start();
        }
    }
}