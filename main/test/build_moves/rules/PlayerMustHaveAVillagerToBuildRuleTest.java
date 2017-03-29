package build_moves.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.build_moves.rules.BuildActionRule;
import tigerisland.build_moves.rules.PlayerMustHaveAVillagerToBuildRule;
import tigerisland.player.Player;

public class PlayerMustHaveAVillagerToBuildRuleTest {

    private BuildActionRule villagersRule;
    final String NOT_ENOUGH_VILLAGERS_ERROR_MESSAGE = "Player does not have enough villagers";

    @Before
    public void setup() {
        this.villagersRule = new PlayerMustHaveAVillagerToBuildRule();
    }

    @Test
    public void test_ShouldReturnUnsuccessfulBuildActionWhenPlayerDoesNotHaveVillagers() {

        // Arrange

        int villagerCount = 0;
        Player player = new Player(villagerCount, 0, 0);
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        // Act
        BuildActionResult result = villagersRule.applyRule(buildActionData);

        // Assert

        Assert.assertFalse(result.successful);
        Assert.assertEquals(NOT_ENOUGH_VILLAGERS_ERROR_MESSAGE, result.errorMessage);
    }

    @Test
    public void test_ShouldReturnSuccessfulBuildActionWhenPlayerHasOneVillagerOrMore() {

        // Arrange
        int villagerCount = 1;
        Player player = new Player(villagerCount, 0, 0);
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        // Act
        BuildActionResult result = villagersRule.applyRule(buildActionData);

        // Assert
        Assert.assertTrue(result.successful);
        Assert.assertNotEquals(NOT_ENOUGH_VILLAGERS_ERROR_MESSAGE, result.errorMessage);

    }

}