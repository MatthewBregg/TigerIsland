package tigerisland.build_moves.builds;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.player.PlayerID;
import tigerisland.score.ScoreManager;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.Terrain;
import tigerisland.terrains.Volcano;

import java.util.ArrayList;

public class TotoroBuildTest {

    private BuildAction totoroBuild;
    private Board board;
    private PieceBoard pieceBoard;
    private SettlementBoard settlementBoard;
    private ScoreManager scoreManager;

    @Before
    public void setup() {
        this.board = new HexBoard();
        this.pieceBoard = new PieceBoardImpl();
        this.settlementBoard = new LazySettlementBoard(pieceBoard);
        this.scoreManager = new ScoreManager();
        totoroBuild = new TotoroBuild(board, pieceBoard, settlementBoard, scoreManager);
    }

    @Test
    public void test_ShouldNotBuildWhenPlayerMustHaveTotoroRuleIsApplied() {

        // Arrange
        final String errorMessage = "Player must have at least one totoro to do this build";
        PlayerID playeId = new PlayerID();
        int totoroCount = 0;
        Player player = new Player(0, totoroCount, 0,  playeId);
        Hex hex = new Hex();
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        // Act
        BuildActionResult actionResult =  totoroBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenEmptyHexRuleIsApplied() {

        // Arrange
        final String errorMessage = "Hex is NOT empty";
        Player player = new Player();
        Hex hex = new Hex();
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        Piece piece = new Totoro();
        pieceBoard.addPiece(piece, hexLocation, player.getId());

        // Act
        BuildActionResult actionResult =  totoroBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenCannotBuildOnVolcanoRuleIsApplied() {
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
        BuildActionResult actionResult =  totoroBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenHexAdjacentToSettlementRuleIsApplied() {
        // Arrange
        final String errorMessage = "Totoro build hex must be adjacent to a settlment of size >=5";
        Player player = new Player();
        Hex hex = new Hex();
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        // Act
        BuildActionResult actionResult =  totoroBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenSettlementAlreadyContainsTotoroRuleIsApplied() {
        // Arrange
        final String errorMessage = "Totoro build hex must be adjacent to a settlement that doesn't contain a totoro";
        Player player = new Player();
        Hex hex = new Hex();
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        Piece villager = new Villager();
        ArrayList<Location> surroundingLocations = hexLocation.getSurroundingLocations();
        for(Location location : surroundingLocations)
            pieceBoard.addPiece(villager, location, player.getId());

        Piece totoro = new Totoro();
        pieceBoard.addPiece(totoro, surroundingLocations.get(0), player.getId());

        // Act
        BuildActionResult actionResult =  totoroBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldPlaceTotoroOnHexWhenAllRulesApplied() {

        // Arrange
        Player player = new Player();
        Hex hex = new Hex();
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        Piece villager = new Villager();
        ArrayList<Location> surroundingLocations = hexLocation.getSurroundingLocations();
        for(Location location : surroundingLocations)
            pieceBoard.addPiece(villager, location, player.getId());

        // Act
        BuildActionResult actionResult =  totoroBuild.build(buildActionData);

        // Assert
        Settlement settlement = settlementBoard.getSettlement(hexLocation);
        Assert.assertTrue(actionResult.successful);
        Assert.assertTrue(settlement.isLocationOccupied(hexLocation));
        Assert.assertEquals(player.getId(), settlement.getPlayerID());
        Assert.assertTrue( pieceBoard.getPiece(hexLocation) instanceof Totoro);

    }

    @Test
    public void test_ShouldRemoveOnTotoroFromPlayerWhenAllRulesApplied() {
        // Arrange
        Player player = new Player();
        int totoroCount = player.getTotoroCount();
        Hex hex = new Hex();
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        Piece villager = new Villager();
        ArrayList<Location> surroundingLocations = hexLocation.getSurroundingLocations();
        for(Location location : surroundingLocations)
            pieceBoard.addPiece(villager, location, player.getId());

        // Act
        BuildActionResult actionResult =  totoroBuild.build(buildActionData);

        // Assert
        Assert.assertTrue(actionResult.successful);
        Assert.assertEquals(totoroCount - 1, player.getTotoroCount());
    }

    @Test
    public void test_ShouldScoreTotoroOnWhenWhenAllRulesApplied() {
        // Arrange
        Player player = new Player();
        Hex hex = new Hex();
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        Piece villager = new Villager();
        ArrayList<Location> surroundingLocations = hexLocation.getSurroundingLocations();
        for(Location location : surroundingLocations)
            pieceBoard.addPiece(villager, location, player.getId());

        // Act
        BuildActionResult actionResult =  totoroBuild.build(buildActionData);

        // Assert
        int expectedScore = 200;
        Assert.assertTrue(actionResult.successful);
        Assert.assertEquals(expectedScore, scoreManager.getPlayerScore(player.getId()) );
    }

}
