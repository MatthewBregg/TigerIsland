package tigerisland.score; /**
 * Created by christinemoore on 3/14/17.
 */

import org.junit.*;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;

public class ScoreTest {
    public static Score scoreToTest;
    public static PlayerID pID;

    @BeforeClass
    public static void makeScoreInstance(){
        Player player = new Player();
        pID = player.getId();

        scoreToTest = new Score(player);
    }

    @Test
    public void settingScoreWorks(){
        scoreToTest.setScore(80);

        Assert.assertTrue(scoreToTest.getScore() == 80);
    }

    @Test
    public void addingPointsToScoreWorks(){
        scoreToTest.addPointsToScore(2);

        Assert.assertTrue(scoreToTest.getScore() == 82);
    }

    @Test
    public void getPlayer(){
        Assert.assertTrue(scoreToTest.getPlayer().getId() == pID);
    }

}
