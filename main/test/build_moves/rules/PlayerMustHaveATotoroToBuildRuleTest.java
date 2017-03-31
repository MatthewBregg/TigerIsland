package build_moves.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.build_moves.rules.BuildActionRule;
import tigerisland.build_moves.rules.PlayerMustHaveATotoroToBuildRule;
import tigerisland.player.Player;

public class PlayerMustHaveATotoroToBuildRuleTest {
    private BuildActionRule mustHaveTotoroRule = null;
    private Player player = null;

    @Before
    public void setup() {

        this.mustHaveTotoroRule = new PlayerMustHaveATotoroToBuildRule();
    }

    @Test
    public void test_ShouldPreventPlayerWithInsufficientTotoroFromBuilding (){

        // Arrange
        final String Error_Message = "Player must have at least one totoro to do this build";

        player = new Player(0,0,0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .build();


        // Act
        BuildActionResult result = mustHaveTotoroRule.applyRule(buildActionData);

        // Assert
        Assert.assertFalse(result.successful);
        Assert.assertEquals(Error_Message, result.errorMessage);
    }
    @Test
    public void test_ShouldAllowPlayerWithSufficientTotoroToBuild (){

        // Arrange
        final String Error_Message = "Player must have at least one tiger to do this build";

        player = new Player(0,1,0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .build();


        // Act
        BuildActionResult result = mustHaveTotoroRule.applyRule(buildActionData);

        // Assert
        Assert.assertTrue(result.successful);
        Assert.assertNotEquals(Error_Message, result.errorMessage);
    }

}