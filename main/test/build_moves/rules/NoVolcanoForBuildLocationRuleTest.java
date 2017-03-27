package build_moves.rules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.BuildActionResult;
import tigerisland.build_moves.rules.BuildActionRule;
import tigerisland.build_moves.rules.NoVolcanoForBuildLocationRule;
import tigerisland.hex.Hex;
import tigerisland.terrains.Rocky;
import tigerisland.terrains.Volcano;

public class NoVolcanoForBuildLocationRuleTest {

    private Board board;
    BuildActionRule volcanoHexRule;

    @Before
    public void setup() {

        board = new HexBoard();
        volcanoHexRule = new NoVolcanoForBuildLocationRule(board);
    }

    @Test
    public void test_ShouldReturnUnsuccessfulBuildActionWhenBuildIsOnAVolcanoHex() {

        // Arrange
        final String VOLCANO_HEX_ERROR_MESSAGE = "Cannot build_moves on a volcano hex";
        Hex volcanoHex = new Hex(Volcano.getInstance());
        Location hexLocation = new Location(0, 0, 0);
        board.placeHex(hexLocation, volcanoHex);

        BuildActionData buildActionData = new BuildActionData.Builder()
                                                .withHexLocation(hexLocation)
                                                .build();
        // Act
        BuildActionResult result = volcanoHexRule.applyRule(buildActionData);

        // Assert
        Assert.assertFalse(result.successful);
        Assert.assertEquals(VOLCANO_HEX_ERROR_MESSAGE, result.errorMessage);
    }

    @Test
    public void test_ShouldReturnSuccessfulBuildActionWhenBuildIsNotOnAVolcanoHex() {

        // Arrange
        Hex volcanoHex = new Hex(Rocky.getInstance());
        Location hexLocation = new Location(0, 0, 0);
        board.placeHex(hexLocation, volcanoHex);

        BuildActionData buildActionData = new BuildActionData.Builder()
                                                .withHexLocation(hexLocation)
                                                .build();
        // Act
        BuildActionResult result = volcanoHexRule.applyRule(buildActionData);

        // Assert
        Assert.assertTrue(result.successful);
    }
}
