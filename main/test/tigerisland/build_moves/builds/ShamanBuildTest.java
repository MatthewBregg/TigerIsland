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

public class ShamanBuildTest {

    private BuildAction shamanBuild;
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
        shamanBuild = new ShamanBuild(board, pieceBoard, settlementBoard, scoreManager);
    }

    @Test
    public void test_ShouldNotBuildWhenPlayerMustHaveShamanRuleIsApplied() {

        // Arrange
        final String errorMessage = "Player must have at least one shaman to do this build";
        PlayerID playeId = new PlayerID();
        int shamanCount = 0;
        Player player = new Player(0, 0, shamanCount, 1, playeId);
        Hex hex = new Hex(0);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        // Act
        BuildActionResult actionResult =  shamanBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenEmptyHexRuleIsApplied() {

        // Arrange
        final String errorMessage = "Hex does not exist on board.";
        Player player = new Player();
        Hex hex = new Hex(0);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

        Piece piece = new Shaman();
        pieceBoard.addPiece(piece, hexLocation, player.getId());

        // Act
        BuildActionResult actionResult =  shamanBuild.build(buildActionData);

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
        BuildActionResult actionResult =  shamanBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }


    @Test
    public void test_ShouldPlaceShamanOnHexWhenAllRulesApplied() {

        // Arrange
        Player player = new Player();
        Hex hex = new Hex(0); hex.setLevel(4);
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
        BuildActionResult actionResult =  shamanBuild.build(buildActionData);

        // Assert
        Settlement settlement = settlementBoard.getSettlement(hexLocation);
        Assert.assertTrue(actionResult.successful);
        Assert.assertTrue(settlement.isLocationOccupied(hexLocation));
        Assert.assertEquals(player.getId(), settlement.getPlayerID());
        Assert.assertTrue( pieceBoard.getPiece(hexLocation) instanceof Shaman);

    }

    @Test
    public void test_ShouldRemoveTigerFromPlayerWhenAllRulesApplied() {
        // Arrange
        Player player = new Player();
        int shamanAmmount = player.getShamanCount();
        Hex hex = new Hex(0); hex.setLevel(4);
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
        BuildActionResult actionResult =  shamanBuild.build(buildActionData);

        // Assert
        Assert.assertTrue(actionResult.successful);
        Assert.assertEquals(shamanAmmount - 1, player.getShamanCount());
    }

    @Test
    public void test_ShouldScoreTigerOnWhenWhenAllRulesApplied() {
        // Arrange
        Player player = new Player();
        Hex hex = new Hex(0); hex.setLevel(3);
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
        BuildActionResult actionResult =  shamanBuild.build(buildActionData);

        // Assert
        int expectedScore = 1;
        Assert.assertTrue(actionResult.successful);
        Assert.assertEquals(expectedScore, scoreManager.getPlayerScore(player.getId()) );
    }
    @Test
    public void test_ShouldFailForNonBoardHex() {
        // Arrange

        final String errorMessage = "Hex does not exist on board.";

        Player player = new Player();
        Hex hex = new Hex(0); hex.setLevel(3);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                .withPlayer(player)
                .withHexLocation(hexLocation)
                .build();

// Notice unplaced hex       board.placeHex(hexLocation, hex);

        Piece villager = new Villager();
        ArrayList<Location> surroundingLocations = hexLocation.getSurroundingLocations();
        for(Location location : surroundingLocations)
            pieceBoard.addPiece(villager, location, player.getId());

        // Act
        BuildActionResult actionResult =  shamanBuild.build(buildActionData);

        // Assert

        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

}