package tigerisland.build_moves.builds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.actions.MakeBuildAction;
import tigerisland.build_moves.actions.PlaceVillagerOnHexAction;
import tigerisland.build_moves.rules.*;
import tigerisland.hex.Hex;
import tigerisland.piece.NullPiece;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.piece.Villager;
import tigerisland.player.Player;
import tigerisland.score.ScoreManager;
import tigerisland.terrains.Grassland;
import tigerisland.terrains.Terrain;
import tigerisland.terrains.Volcano;

import java.util.List;

public class FoundNewSettlementBuildTest {

    private FoundNewSettlementBuild foundNewSettlementBuildAction;
    private Board board;
    private PieceBoard pieceBoard;
    private ScoreManager scoreManager;

    @Before
    public void setup() {
        this.board = new HexBoard();
        this.pieceBoard = new PieceBoardImpl();
        this.scoreManager = new ScoreManager();
        foundNewSettlementBuildAction = new FoundNewSettlementBuild(board, pieceBoard, scoreManager);
    }

    @Test
    public void test_ShouldNotBuildWhenFindHexOnBoardRuleIsApplied() {

        // Arrange
        final String errorMessage = "Hex does not exist on board.";
        Player player = new Player();
        Hex hex = new Hex(0);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        // Act
        BuildActionResult actionResult =  foundNewSettlementBuildAction.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenEmptyHexRuleIsApplied() {

        // Arrange
        final String errorMessage = "Hex is NOT empty";
        Player player = new Player();
        Terrain grassland = Grassland.getInstance();
        Hex hex = new Hex(grassland);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);
        pieceBoard.addPiece(new Villager(), hexLocation, player.getId());

        // Act
        BuildActionResult actionResult =  foundNewSettlementBuildAction.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenHexLevelOneRuleIsApplied() {

        // Arrange
        final String errorMessage = "Hex is NOT a level one hex.";
        Player player = new Player();
        Terrain grassland = Grassland.getInstance();
        Hex hex = new Hex(grassland); hex.setLevel(2);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        // Act
        BuildActionResult actionResult =  foundNewSettlementBuildAction.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenVolcanoOnBuildLocationRuleIsApplied() {

        // Arrange
        final String errorMessage = "Cannot build on a volcano hex";
        Player player = new Player();
        Terrain volcano = Volcano.getInstance();
        Hex hex = new Hex(volcano);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        // Act
        BuildActionResult actionResult =  foundNewSettlementBuildAction.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenNotEnoughVillagersRuleIsApplied() {

        // Arrange
        final String errorMessage = "Player does not have enough villagers";
        Player player = new Player();
        int playersVillagers = player.getVillagerCount();
        Terrain grassland = Grassland.getInstance();
        Hex hex = new Hex(grassland);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);
        player.removeVillagers(playersVillagers);

        // Act
        BuildActionResult actionResult =  foundNewSettlementBuildAction.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldPlaceVillagerOnHexWhenAllRulesApplied() {

        // Arrange
        Player player = new Player();
        Terrain grassland = Grassland.getInstance();
        Hex hex = new Hex(grassland);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        // Act
        BuildActionResult actionResult =  foundNewSettlementBuildAction.build(buildActionData);

        // Assert
        Assert.assertTrue(actionResult.successful);
        Assert.assertEquals(hex, board.getHex(hexLocation));
    }

    @Test
    public void test_PlayerShouldHaveOnLessVillagerWhenAllRulesApplied() {
        // Arrange
        Player player = new Player();
        int playersVillagers = player.getVillagerCount();
        Terrain grassland = Grassland.getInstance();
        Hex hex = new Hex(grassland);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);
        // Act
        BuildActionResult actionResult =  foundNewSettlementBuildAction.build(buildActionData);

        // Assert
        Assert.assertTrue(actionResult.successful);
        Assert.assertEquals(playersVillagers - 1, player.getVillagerCount());
    }

    @Test
    public void test_ShouldScoreVillagerOnHexWhenAllRulesApplied() {
        // Arrange
        Player player = new Player();
        Terrain grassland = Grassland.getInstance();
        Hex hex = new Hex(grassland);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        // Act
        BuildActionResult actionResult =  foundNewSettlementBuildAction.build(buildActionData);

        // Assert
        Assert.assertTrue(actionResult.successful);
        Assert.assertEquals(hex.getLevel(), scoreManager.getPlayerScore(player.getId()));
    }

}
