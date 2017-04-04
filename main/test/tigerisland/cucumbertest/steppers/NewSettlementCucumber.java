package tigerisland.cucumbertest.steppers;

import org.junit.Assert;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.build_moves.builds.BuildActionData;
import tigerisland.build_moves.builds.FoundNewSettlementBuild;
import tigerisland.hex.Hex;
import tigerisland.piece.PieceBoard;
import tigerisland.piece.PieceBoardImpl;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.terrains.Grassland;

public class NewSettlementCucumber {

    FoundNewSettlementBuild foundNewSettlementBuild;
    Board board;
    PieceBoard pieceBoard;
    Player player;
    Location buildHexLocation;
    Hex boardHex;

    private int playerPreviousVillagerCount;

    //Given Player has at least 1 villager
    public void playerHasAtLeastOneVillage() {

        // Arrange
        player = new Player();
        playerPreviousVillagerCount = player.getVillagerCount();

        // Assert
        Assert.assertTrue(player.getVillagerCount() >= 1);
    }


    // board has a non-volcano hex
    public void boardHasANonVolcanoHex() {
        boardHex = new Hex(Grassland.getInstance());
    }

    // And The hex has a level of 1
    public void theHexHasALevelOne() {
        boardHex.setLevel(1);
    }

    // And The hex is unoccupied
    public void theHexIsUnoccupied() {

        // Arrange
        board = new HexBoard();
        pieceBoard = new PieceBoardImpl();
        Location location = new Location(0, 0, 0);

        // Act
        board.placeHex( location, boardHex);

        // Assert
        Assert.assertFalse(pieceBoard.isLocationOccupied(location));
    }

    //When Player attempts to place the villager on a non-volcano hex
    public void playerAttemptsToPlaceVillagerOnAHex() {

        // Arrange
        Location buildHexLocation = new Location(0, 0, 0);
        BuildActionData buildActionData = new BuildActionData.Builder()
                                                .withPlayer(player)
                                                .withHexLocation(buildHexLocation)
                                                .build();

        // Act
        foundNewSettlementBuild.build(buildActionData);
    }



    //Then A new settlement of size 1 is formed on that tile,
    public void aNewSettlementOfSizeOneIsFormed() {

        int expectedSettlmentSize = 1;
        LazySettlementBoard lazySettlement = new LazySettlementBoard(pieceBoard);
        Settlement settlement = lazySettlement.getSettlement(buildHexLocation, player.getId());

        Assert.assertEquals(expectedSettlmentSize, settlement.settlementSize());
    }


    //And One villager is subtracted from players villager count
    public void oneVillagerIsSubtractedFromPlayersVillagerCount() {
        Assert.assertEquals(playerPreviousVillagerCount-1, player.getVillagerCount());
    }






}
