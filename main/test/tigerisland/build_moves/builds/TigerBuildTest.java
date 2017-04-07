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

public class TigerBuildTest {

    private BuildAction tigerBuild;
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
        tigerBuild = new TigerBuild(board, pieceBoard, settlementBoard, scoreManager);
    }

//    @Override
//    protected List<BuildActionRule> createBuildActionRules() {
//        List<BuildActionRule> rules = new ArrayList<>();
//        //Todo add BuildLocationOnBoard
//        rules.add( new PlayerMustHaveATigerToBuildRule());
//        rules.add( new EmptyHexRule(pieceBoard));
//        rules.add( new CannotBuildOnVolcanoRule(board));
//        rules.add(new TigerBuildHexMustBeLevelThreeRule(board));
//        rules.add(new SettlementAlreadyContainsTigerRule(settlementBoard));
//        return rules;
//    }
//
//    @Override
//    protected List<MakeBuildAction> createBuildActions() {
//        List<MakeBuildAction> actions = new ArrayList<>();
//        actions.add( new PlaceTigerOnHexAction(pieceBoard));
//        actions.add( new ScoreTigerOnHex(scoreManager));
//        return actions;
//    }

    @Test
    public void test_ShouldNotBuildWhenPlayerMustHaveTigerRuleIsApplied() {

        // Arrange
        final String errorMessage = "Player must have at least one tiger to do this build";
        PlayerID playeId = new PlayerID();
        int tigerCount = 0;
        Player player = new Player(0, 0, tigerCount,  playeId);
        Hex hex = new Hex(0);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        // Act
        BuildActionResult actionResult =  tigerBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenEmptyHexRuleIsApplied() {

        // Arrange
        final String errorMessage = "Hex is NOT empty";
        Player player = new Player();
        Hex hex = new Hex(0);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        Piece piece = new Totoro();
        pieceBoard.addPiece(piece, hexLocation, player.getId());

        // Act
        BuildActionResult actionResult =  tigerBuild.build(buildActionData);

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
        BuildActionResult actionResult =  tigerBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenTigerHexLevelRuleIsApplied() {
        // Arrange
        final String errorMessage = "The tiger build hex must be level 3 or greater";
        Player player = new Player();
        Hex hex = new Hex(0); hex.setLevel(1);
        Location hexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                        .withPlayer(player)
                                        .withHexLocation(hexLocation)
                                        .build();

        board.placeHex(hexLocation, hex);

        // Act
        BuildActionResult actionResult =  tigerBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldNotBuildWhenSettlementAlreadyContainsTigerRuleIsApplied() {
        // Arrange
        final String errorMessage = "Tiger build hex must be adjacent to a settlement that doesn't contain a tiger";
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

        Piece tiger = new Tiger();
        pieceBoard.addPiece(tiger, surroundingLocations.get(0), player.getId());

        // Act
        BuildActionResult actionResult =  tigerBuild.build(buildActionData);

        // Assert
        Assert.assertFalse(actionResult.successful);
        Assert.assertEquals(errorMessage, actionResult.errorMessage);
    }

    @Test
    public void test_ShouldPlaceTigerOnHexWhenAllRulesApplied() {

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
        BuildActionResult actionResult =  tigerBuild.build(buildActionData);

        // Assert
        Settlement settlement = settlementBoard.getSettlement(hexLocation);
        Assert.assertTrue(actionResult.successful);
        Assert.assertTrue(settlement.isLocationOccupied(hexLocation));
        Assert.assertEquals(player.getId(), settlement.getPlayerID());
        Assert.assertTrue( pieceBoard.getPiece(hexLocation) instanceof Tiger);

    }

    @Test
    public void test_ShouldRemoveTigerFromPlayerWhenAllRulesApplied() {
        // Arrange
        Player player = new Player();
        int tigerAmount = player.getTigerCount();
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
        BuildActionResult actionResult =  tigerBuild.build(buildActionData);

        // Assert
        Assert.assertTrue(actionResult.successful);
        Assert.assertEquals(tigerAmount - 1, player.getTigerCount());
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
        BuildActionResult actionResult =  tigerBuild.build(buildActionData);

        // Assert
        int expectedScore = 75;
        Assert.assertTrue(actionResult.successful);
        Assert.assertEquals(expectedScore, scoreManager.getPlayerScore(player.getId()) );
    }
}