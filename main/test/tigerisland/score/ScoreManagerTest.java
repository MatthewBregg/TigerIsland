package tigerisland.score; /**
 * Created by christinemoore on 3/22/17.
 */

import org.junit.*;
import tigerisland.player.Player;
import tigerisland.hex.Hex;
import tigerisland.player.PlayerID;
import tigerisland.terrains.Rocky;


public class ScoreManagerTest {
    private static ScoreManager scoreManager;
    private static Score p1Score;
    private static Score p2Score;
    private static Hex testHexLevel1;
    private static Hex testHexLevel3;

    @BeforeClass
    public static void makeStartingInstances(){
        Player player1 = new Player();
        Player player2 = new Player();

        scoreManager = new ScoreManager(player1, player2);

        testHexLevel1 = new Hex();
        testHexLevel3 = new Hex(5, 5, Rocky.getInstance(), 3);
    }

    @Test
    public void addingScoreNewSettlementToBothPlayers(){
        scoreManager.addBuildNewSettlementScore(scoreManager.getPlayer1ID());
        scoreManager.addBuildNewSettlementScore(scoreManager.getPlayer2ID());

        Assert.assertTrue((scoreManager.getPlayer1Score() == 1) && (scoreManager.getPlayer2Score() == 1));
    }

    @Test
    public void checkResetScoreWorks(){
        scoreManager.resetPlayerScores();
        Assert.assertTrue((scoreManager.getPlayer1Score() == 0) && (scoreManager.getPlayer2Score() == 0));
    }

    @Test
    public void addingPointsForSingleHexDueToExpansion(){
        scoreManager.resetPlayerScores();
        scoreManager.addMeeplePlacementScoreDueToExpansion(scoreManager.getPlayer1ID(), testHexLevel1);
        Assert.assertTrue(scoreManager.getPlayer1Score() == 1);
    }

    @Test
    public void addingPointsForSingleHexLevel3DueToExpansion(){
        scoreManager.resetPlayerScores();
        scoreManager.addMeeplePlacementScoreDueToExpansion(scoreManager.getPlayer2ID(), testHexLevel3);
        Assert.assertTrue(scoreManager.getPlayer2Score() == 9);
    }

    @Test
    public void addPointsForAddingTotoroToSettlement(){
        scoreManager.resetPlayerScores();
        scoreManager.addTotoroScore(scoreManager.getPlayer1ID());
        Assert.assertTrue(scoreManager.getPlayer1Score() == 200);
    }

    @Test
    public void addPointsForAddingTigerToSettlement(){
        scoreManager.resetPlayerScores();
        scoreManager.addTigerScore(scoreManager.getPlayer2ID());
        Assert.assertTrue(scoreManager.getPlayer2Score() == 75);
    }



}
