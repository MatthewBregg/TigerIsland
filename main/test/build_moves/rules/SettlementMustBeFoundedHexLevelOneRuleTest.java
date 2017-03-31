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
import tigerisland.build_moves.rules.SettlementMustBeFoundedHexLevelOneRule;
import tigerisland.hex.Hex;
import tigerisland.player.Player;

public class SettlementMustBeFoundedHexLevelOneRuleTest {

    private BuildActionRule hexLevelOneRule;
    private Board board;

    @Before
    public void setup() {

        this.board = new HexBoard();
        this.hexLevelOneRule = new SettlementMustBeFoundedHexLevelOneRule(board);
    }

    @Test
    public void test_ShouldReturnUnsuccessfulWhenHexIsNotOnBoard() {

        // Arrange
        final String LEVEL_ONE_HEX_ERROR_MESSAGE = "Hex is NOT a level one hex.";

        Player player = new Player();
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        Hex hex = new Hex();
        hex.setLevel(2);
        board.placeHex(hexLocation, hex);

        // Act
        BuildActionResult result = hexLevelOneRule.applyRule(buildActionData);

        // Assert
        Assert.assertFalse(result.successful);
        Assert.assertEquals(LEVEL_ONE_HEX_ERROR_MESSAGE, result.errorMessage);
    }

    @Test
    public void test_ShouldReturnSuccessfulWhenPieceBoardAtLocationIsNotUsed() {

        // Arrange
        final String EMPTY_HEX_ERROR_MESSAGE = "Hex is not on board";
        Player player = new Player();
        Location hexLocation = new Location(0, 0, 0);

        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        Hex hex = new Hex();
        hex.setLevel(1);
        board.placeHex(hexLocation, hex);

        // Act
        BuildActionResult result = hexLevelOneRule.applyRule(buildActionData);

        // Assert
        Assert.assertTrue(result.successful);
    }
}
