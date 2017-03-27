package tigerisland.score; /**
 * Created by christinemoore on 3/22/17.
 */

import org.junit.*;
import tigerisland.player.Player;
import tigerisland.hex.Hex;
import tigerisland.player.PlayerID;
import tigerisland.terrains.Rocky;
import tigerisland.score.ScoreManager;


public class ScoreManagerTest {
    private static ScoreManager playerScores;
    private static Player player1;
    private static Player player2;
    private static Hex testHexLevel3;

    @BeforeClass
    public static void makeStartingInstances(){
        playerScores = new ScoreManager();
        player1 = new Player();
        player2 = new Player();
        testHexLevel3 = new Hex(5, 5, Rocky.getInstance(), 3);
    }

    @Test
    public void adding2PlayersToScoreManager(){
        playerScores.addNewPlayer(player1.getId());
        playerScores.addNewPlayer(player2.getId());

        Assert.assertTrue(playerScores.getTotalNumberOfPlayers() == 2);
    }

    @Test
    public void makeSureNewPlayersHaveAZeroScorePointValue(){
        playerScores.resetPlayerScore(player1.getId());
        playerScores.resetPlayerScore(player2.getId());
        int player1Score = playerScores.getPlayerScore(player1.getId());
        System.out.println(player1Score);
        int player2Score = playerScores.getPlayerScore(player2.getId());
        System.out.println(player2Score);
        Assert.assertTrue((player1Score == 0) && (player2Score == 0));
    }

    @Test
    public void addingPointsForNewSettlement(){
        playerScores.resetPlayerScore(player1.getId());
        playerScores.buildOnNewHex(player1.getId(), 1);
        Assert.assertTrue(playerScores.getPlayerScore(player1.getId()) == 1);
    }

    @Test
    public void addingPointsForSingleHexLevel3DueToExpansion(){
        playerScores.resetPlayerScore(player1.getId());
        playerScores.buildOnNewHex(player1.getId(), 3);
        Assert.assertTrue(playerScores.getPlayerScore(player1.getId()) == 9);
    }

    @Test
    public void addPointsForAddingTotoroToSettlement(){
        playerScores.resetPlayerScore(player2.getId());
        playerScores.addTotoroScore(player2.getId());
        Assert.assertTrue(playerScores.getPlayerScore(player2.getId()) == 200);
    }

    @Test
    public void addPointsForAddingTigerToSettlement(){
        playerScores.resetPlayerScore(player2.getId());
        playerScores.addTigerScore(player2.getId());
        Assert.assertTrue(playerScores.getPlayerScore(player2.getId()) == 75);
    }

}
