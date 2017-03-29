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
import tigerisland.build_moves.rules.TigerBuildHexMustBeLevelThreeRule;
import tigerisland.hex.Hex;

public class TigerBuildHexMustBeLevelThreeRuleTest {

    private Board board = null;
    private Hex hex = null;
    private Location location = null;
    private BuildActionRule rule = null;
    private BuildActionData buildData = null;
    private final String errorMessage = "The tiger build hex must be level 3";
    private BuildActionResult result = null;


    @Before
    public void setUp() throws Exception {
        board = new HexBoard();
    }

    @Test
    public void test_ShouldPreventTigerBuildOnLevelLessThanThree() throws Exception {

        //Arrange
        location = new Location(0,0,0);
        hex = new Hex(0, 0, null, 2);
        board.placeHex(location, hex);
        buildData = new BuildActionData.Builder().withHexLocation(location).build();

        //Act

        rule = new TigerBuildHexMustBeLevelThreeRule(board);
        result = rule.applyRule(buildData);

        //Assert

        Assert.assertFalse(result.successful);

        Assert.assertEquals(errorMessage, result.errorMessage);

    }
    @Test
    public void test_ShouldAllowTigerBuildOnLevelGreaterThanEqualsToThree() throws Exception {

        //Arrange
        location = new Location(0,0,0);
        hex = new Hex(1, 0, null, 3);
        board.placeHex(location, hex);
        buildData = new BuildActionData.Builder().withHexLocation(location).build();

        //Act

        rule = new TigerBuildHexMustBeLevelThreeRule(board);
        result = rule.applyRule(buildData);

        //Assert

        Assert.assertTrue(result.successful);

        Assert.assertNotEquals(errorMessage, result.errorMessage);

    }


}