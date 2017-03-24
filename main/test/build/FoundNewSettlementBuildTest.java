package build;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.build.BuildActionData;
import tigerisland.build.BuildActionResult;
import tigerisland.build.FoundNewSettlementBuild;
import tigerisland.build.rules.BuildActionRule;
import tigerisland.build.rules.NotEnoughVillagersRule;
import tigerisland.player.Player;

public class FoundNewSettlementBuildTest {

    private FoundNewSettlementBuild foundNewSettlementBuild;
    private BuildActionRule villagerRules;

    @Before
    public void setup() {

        villagerRules = new NotEnoughVillagersRule();
        foundNewSettlementBuild = new FoundNewSettlementBuild(villagerRules);
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
        BuildActionResult result = foundNewSettlementBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(result.successful);
        Assert.assertFalse(result.errorMessage.isEmpty());
    }

    @Test
    public void test_ShouldReturnSuccessfulBuildActionWhenPlayerHasOneVillager() {

        // Arrange
        int villagerCount = 1;
        Player player = new Player(villagerCount, 0, 0);
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                                                .withPlayer(player)
                                                .withHexLocation(hexLocation)
                                                .build();

        // Act
        BuildActionResult result = foundNewSettlementBuild.build(buildActionData);

        // Assert
        Assert.assertTrue(result.successful);
        Assert.assertTrue(result.errorMessage.isEmpty());
    }


}
