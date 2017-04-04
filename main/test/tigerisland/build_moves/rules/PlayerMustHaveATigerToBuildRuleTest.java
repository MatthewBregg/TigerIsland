package tigerisland.build_moves.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.build_moves.rules.BuildActionRule;
import tigerisland.build_moves.rules.PlayerMustHaveATigerToBuildRule;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;

public class PlayerMustHaveATigerToBuildRuleTest {

    private BuildActionRule mustHaveTigerRule = null;
    private Player player = null;

    @Before
    public void setup() {

        this.mustHaveTigerRule = new PlayerMustHaveATigerToBuildRule();
    }

    @Test
    public void test_ShouldPreventPlayerWithInsufficientTigersFromBuilding (){

        // Arrange
        final String Error_Message = "Player must have at least one tiger to do this build";

        player = new Player(0,0,0, new PlayerID());

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .build();


        // Act
        BuildActionResult result = mustHaveTigerRule.applyRule(buildActionData);

        // Assert
        Assert.assertFalse(result.successful);
        Assert.assertEquals(Error_Message, result.errorMessage);
    }
    @Test
    public void test_ShouldAllowPlayerWithSufficientTigersToBuild (){

        // Arrange
        final String Error_Message = "Player must have at least one tiger to do this build";

        player = new Player(0,0,1, new PlayerID());

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .build();


        // Act
        BuildActionResult result = mustHaveTigerRule.applyRule(buildActionData);

        // Assert
        Assert.assertTrue(result.successful);
        Assert.assertNotEquals(Error_Message, result.errorMessage);
    }

}