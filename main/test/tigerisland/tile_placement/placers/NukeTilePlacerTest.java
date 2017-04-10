package tigerisland.tile_placement.placers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.piece.*;
import tigerisland.player.Player;
import tigerisland.settlement.LazySettlementBoard;
import tigerisland.settlement.Settlement;
import tigerisland.settlement.SettlementBoard;
import tigerisland.terrains.*;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.exceptions.*;
import tigerisland.FirstTilePlacer;
import tigerisland.tile_placement.rules.*;

public class NukeTilePlacerTest {

    Board board;
    PieceBoard pieceBoard;
    SettlementBoard settlementBoard;

    TilePlacement invalidTilePlacement;
    NukeTilePlacer nukeTilePlacer;
    FirstTilePlacer firstTilePlacer;


    NukePlacementRule nukeCoverHexesLevelRule;
    NukePlacementRule nukeHexesOfDifferentTilesRule;
    NukePlacementRule nukeSettlementEradicationRule;
    NukePlacementRule nukeVolcanoOnVolcanoRule;
    NukePlacementRule nukeNonNukeablePieceRule;

    @Before
    public void setup() {
        this.board = new HexBoard();
        this.pieceBoard = new PieceBoardImpl();
        this.settlementBoard = new LazySettlementBoard(pieceBoard);

        invalidTilePlacement = new InvalidTilePlacer();

        nukeCoverHexesLevelRule  = new NukeCoverHexesLevelRule(board);
        nukeHexesOfDifferentTilesRule = new NukeHexesOfDifferentTilesRule(board);
        nukeVolcanoOnVolcanoRule = new NukeVolcanoOnVolcanoRule(board);
        nukeNonNukeablePieceRule = new NukeNonNukeablePieceRule(pieceBoard);
        nukeSettlementEradicationRule = new NukeSettlementEradicationRule(settlementBoard);

        nukeTilePlacer = new NukeTilePlacer(
                board,
                pieceBoard,
                nukeCoverHexesLevelRule,
                nukeHexesOfDifferentTilesRule,
                nukeVolcanoOnVolcanoRule,
                nukeNonNukeablePieceRule,
                nukeSettlementEradicationRule);

        nukeTilePlacer.setNextTilePlacement(invalidTilePlacement);

        firstTilePlacer = new FirstTilePlacer(board);
        firstTilePlacer.setNextTilePlacement(nukeTilePlacer);

    }

    @Test(expected = InvalidTilePlacementException.class)
    public void test_ShouldCallNextTilePlaceWhenBoardIsEmpty() throws TilePlacementException{

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);

        // Act
        this.nukeTilePlacer.placeTile(tile, location);

        // Assert
        int expectedBoardSize = 0;
        Assert.assertEquals(expectedBoardSize, board.getSize());
    }

    @Test(expected = NukeCoverHexesLevelRuleException.class)
    public void test_ShouldThrowExceptionWhenNukeHexesLevelRuleIsApplied() throws TilePlacementException{

        // Arrange
        Hex volcanoHex = new Hex(Volcano.getInstance()); volcanoHex.setLevel(1);
        Hex grasslandHex = new Hex(Grassland.getInstance()); grasslandHex.setLevel(1);
        Hex rockyHex = new Hex(Rocky.getInstance()); rockyHex.setLevel(2);

        Location referenceLocation = new Location(0, 0, 0);
        Location southEastLocation = referenceLocation.getAdjacent(Orientation.getSouthEast());
        Location southWestLocation = referenceLocation.getAdjacent(Orientation.getSouthWest());

        board.placeHex(referenceLocation, volcanoHex);
        board.placeHex(southEastLocation, grasslandHex);
        board.placeHex(southWestLocation, rockyHex);

        int tileId = 1;
        Tile tile = new Tile(tileId, Lake.getInstance(), Jungle.getInstance());
        Orientation tileOrientation = new Orientation(Orientation.EAST);
        tile.setOrientation(tileOrientation);

        // Act
        this.nukeTilePlacer.placeTile(tile, referenceLocation);
    }

    @Test(expected = NukeHexesOfDifferentTilesRuleException.class)
    public void test_ShouldThrowExceptionWhenHexesOfTilesIsAppliedIsApplied() throws TilePlacementException{

        // Arrange
        int firstTileId = 1;
        Hex volcanoHex = new Hex(firstTileId, Volcano.getInstance()); volcanoHex.setLevel(1);
        Hex grasslandHex = new Hex(firstTileId, Grassland.getInstance()); grasslandHex.setLevel(1);
        Hex rockyHex = new Hex(firstTileId, Rocky.getInstance()); rockyHex.setLevel(1);

        Location referenceLocation = new Location(0, 0, 0);
        Location southEastLocation = referenceLocation.getAdjacent(Orientation.getSouthEast());
        Location southWestLocation = referenceLocation.getAdjacent(Orientation.getSouthWest());

        board.placeHex(referenceLocation, volcanoHex);
        board.placeHex(southEastLocation, grasslandHex);
        board.placeHex(southWestLocation, rockyHex);

        int secondTileId = 3;
        Tile tile = new Tile(secondTileId, Lake.getInstance(), Jungle.getInstance());
        Orientation tileOrientation = new Orientation(Orientation.EAST);
        tile.setOrientation(tileOrientation);

        // Act
        this.nukeTilePlacer.placeTile(tile, referenceLocation);
    }

    @Test(expected = NukeVolcanoOnVolcanoRuleException.class)
    public void test_ShouldThrowExceptionWhenVolcanoOnVolcanoRuleIsApplied() throws TilePlacementException{

        // Arrange
        Hex lakeHex = new Hex(1, Lake.getInstance()); lakeHex.setLevel(1);
        Hex grasslandHex = new Hex(2, Grassland.getInstance()); grasslandHex.setLevel(1);
        Hex rockyHex = new Hex(2, Rocky.getInstance()); rockyHex.setLevel(1);

        Location referenceLocation = new Location(0, 0, 0);
        Location southEastLocation = referenceLocation.getAdjacent(Orientation.getSouthEast());
        Location southWestLocation = referenceLocation.getAdjacent(Orientation.getSouthWest());

        board.placeHex(referenceLocation, lakeHex);
        board.placeHex(southEastLocation, grasslandHex);
        board.placeHex(southWestLocation, rockyHex);

        int secondTileId = 3;
        Tile tile = new Tile(secondTileId, Lake.getInstance(), Jungle.getInstance());
        Orientation tileOrientation = new Orientation(Orientation.EAST);
        tile.setOrientation(tileOrientation);

        // Act
        this.nukeTilePlacer.placeTile(tile, referenceLocation);
    }

    @Test(expected = NukeTotoroRuleException.class)
    public void test_ShouldThrowExceptionWhenNukeNonNukeablePieceRuleIsApplied() throws TilePlacementException{
        // Arrange
        Hex volcanoHex = new Hex(1, Volcano.getInstance()); volcanoHex.setLevel(1);
        Hex grasslandHex = new Hex(2, Grassland.getInstance()); grasslandHex.setLevel(1);
        Hex rockyHex = new Hex(2, Rocky.getInstance()); rockyHex.setLevel(1);

        Location referenceLocation = new Location(0, 0, 0);
        Location southEastLocation = referenceLocation.getAdjacent(Orientation.getSouthEast());
        Location southWestLocation = referenceLocation.getAdjacent(Orientation.getSouthWest());

        board.placeHex(referenceLocation, volcanoHex);
        board.placeHex(southEastLocation, grasslandHex);
        board.placeHex(southWestLocation, rockyHex);

        Player player = new Player();
        Piece totoro = new Totoro();
        pieceBoard.addPiece(totoro, referenceLocation, player.getId());

        int secondTileId = 3;
        Tile tile = new Tile(secondTileId, Lake.getInstance(), Jungle.getInstance());
        Orientation tileOrientation = new Orientation(Orientation.EAST);
        tile.setOrientation(tileOrientation);

        // Act
        this.nukeTilePlacer.placeTile(tile, referenceLocation);
    }

    @Test(expected = NukeSettlementEradicationException.class)
    public void test_ShouldThrowExceptionWhenSettlementEradicationRuleIsApplied() throws TilePlacementException{
        // Arrange
        Hex volcanoHex = new Hex(1, Volcano.getInstance()); volcanoHex.setLevel(1);
        Hex grasslandHex = new Hex(2, Grassland.getInstance()); grasslandHex.setLevel(1);
        Hex rockyHex = new Hex(2, Rocky.getInstance()); rockyHex.setLevel(1);

        Location referenceLocation = new Location(0, 0, 0);
        Location southEastLocation = referenceLocation.getAdjacent(Orientation.getSouthEast());
        Location southWestLocation = referenceLocation.getAdjacent(Orientation.getSouthWest());

        board.placeHex(referenceLocation, volcanoHex);
        board.placeHex(southEastLocation, grasslandHex);
        board.placeHex(southWestLocation, rockyHex);

        Player player = new Player();
        Piece villager = new Villager();
        pieceBoard.addPiece(villager, referenceLocation, player.getId());
        pieceBoard.addPiece(villager, southEastLocation, player.getId());
        pieceBoard.addPiece(villager, southWestLocation, player.getId());

        int secondTileId = 3;
        Tile tile = new Tile(secondTileId, Lake.getInstance(), Jungle.getInstance());
        Orientation tileOrientation = new Orientation(Orientation.EAST);
        tile.setOrientation(tileOrientation);

        // Act
        this.nukeTilePlacer.placeTile(tile, referenceLocation);
    }

    @Test()
    public void test_ShouldPlace3HexesOnBoardWithLevel2() throws TilePlacementException{

        // Arrange
        Hex volcanoHex = new Hex(1, Volcano.getInstance()); volcanoHex.setLevel(1);
        Hex grasslandHex = new Hex(2, Grassland.getInstance()); grasslandHex.setLevel(1);
        Hex rockyHex = new Hex(2, Rocky.getInstance()); rockyHex.setLevel(1);

        Location referenceLocation = new Location(0, 0, 0);
        Location southEastLocation = referenceLocation.getAdjacent(Orientation.getSouthEast());
        Location southWestLocation = referenceLocation.getAdjacent(Orientation.getSouthWest());

        board.placeHex(referenceLocation, volcanoHex);
        board.placeHex(southEastLocation, grasslandHex);
        board.placeHex(southWestLocation, rockyHex);

        int secondTileId = 3;
        Tile tile = new Tile(secondTileId, Lake.getInstance(), Jungle.getInstance());
        Orientation tileOrientation = new Orientation(Orientation.EAST);
        tile.setOrientation(tileOrientation);

        // Act
        this.nukeTilePlacer.placeTile(tile, referenceLocation);
        Assert.assertEquals(3, board.getSize());

        Hex volcanoHexFromTile = board.getHex(referenceLocation);
        Assert.assertEquals(2, volcanoHexFromTile.getLevel());

        Hex lakeHexFromTile = board.getHex(southEastLocation);
        Assert.assertEquals(2, lakeHexFromTile.getLevel());

        Hex jungleHexFromTile = board.getHex(southWestLocation);
        Assert.assertEquals(2, jungleHexFromTile.getLevel());
    }

    @Test
    public void test_ShouldRemoveBoardPiecesWhenNuking() throws TilePlacementException {

        // Arrange
        Hex volcanoHex = new Hex(1, Volcano.getInstance()); volcanoHex.setLevel(1);
        Hex grasslandHex = new Hex(2, Grassland.getInstance()); grasslandHex.setLevel(1);
        Hex rockyHex = new Hex(2, Rocky.getInstance()); rockyHex.setLevel(1);

        Location referenceLocation = new Location(0, 0, 0);
        Location southEastLocation = referenceLocation.getAdjacent(Orientation.getSouthEast());
        Location southWestLocation = referenceLocation.getAdjacent(Orientation.getSouthWest());

        board.placeHex(referenceLocation, volcanoHex);
        board.placeHex(southEastLocation, grasslandHex);
        board.placeHex(southWestLocation, rockyHex);

        Player player = new Player();
        Piece villager = new Villager();
        pieceBoard.addPiece(villager, referenceLocation, player.getId());
        pieceBoard.addPiece(villager, southEastLocation, player.getId());
        pieceBoard.addPiece(villager, southWestLocation, player.getId());

        Location eastOfReferenceLocation = referenceLocation.getAdjacent(Orientation.getEast());
        pieceBoard.addPiece(villager, eastOfReferenceLocation, player.getId());

        int secondTileId = 3;
        Tile tile = new Tile(secondTileId, Lake.getInstance(), Jungle.getInstance());
        Orientation tileOrientation = new Orientation(Orientation.EAST);
        tile.setOrientation(tileOrientation);

        // Act
        this.nukeTilePlacer.placeTile(tile, referenceLocation);

        // Assert
        Settlement referenceLocationSettlement = settlementBoard.getSettlement(referenceLocation, player.getId());
        Assert.assertEquals(0, referenceLocationSettlement.settlementSize());

        Settlement leftOverSettlement = settlementBoard.getSettlement(eastOfReferenceLocation, player.getId());
        Assert.assertEquals(1, leftOverSettlement.settlementSize());
    }

}
