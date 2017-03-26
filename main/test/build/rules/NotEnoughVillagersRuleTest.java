package build.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.build.rules.BuildActionRule;
import tigerisland.build.rules.NotEnoughVillagersRule;
import tigerisland.player.Player;

public class NotEnoughVillagersRuleTest {

    private BuildActionRule villagersRule;

    @Before
    public void setup() {
        this.villagersRule = new NotEnoughVillagersRule();
    }

    @Test
    public void test_ShouldReturnUnsuccessfulBuildActionWhenPlayerDoesNotHaveVillagers() {

        // Arrange
        final String NOT_ENOUGH_VILLAGERS_ERROR_MESSAGE = "Player does not have enough villagers";
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
    }

}