package tigerisland.unit.tile_placement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.*;
import tigerisland.tile_placement.exceptions.InvalidTilePlacementException;
import tigerisland.tile_placement.rules.NukeCoverHexesLevelRule;
import tigerisland.tile_placement.rules.NukePlacementRule;

public class NukeTilePlacerTest {

    Board board;

    TilePlacement invalidTilePlacement;
    NukePlacementRule nukeCoverHexesLevelRule;
    NukeTilePlacer nukeTilePlacer;
    AdjacentToBoardTilePlacer adjacentToBoardTilePlacer;

    @Before
    public void setup() {
        this.board = new HexBoard();

        invalidTilePlacement = new InvalidTilePlacer();
        nukeCoverHexesLevelRule  = new NukeCoverHexesLevelRule(board);

        nukeTilePlacer = new NukeTilePlacer(
                board,
                nukeCoverHexesLevelRule);

        nukeTilePlacer.setNextTilePlacement(invalidTilePlacement);

        adjacentToBoardTilePlacer = new AdjacentToBoardTilePlacer(board);
        adjacentToBoardTilePlacer.setNextTilePlacement(nukeTilePlacer);

    }

    @Test(expected = InvalidTilePlacementException.class)
    public void test_ShouldCallNextTilePlaceWhenBoardIsEmpty() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);

        // Act
        this.nukeTilePlacer.placeTile(tile, location);

        // Assert
        int expectedBoardSize = 0;
        Assert.assertEquals(expectedBoardSize, board.getSize());
    }

    @Test()
    public void test_ShouldPlace3HexesOnBoardWithLevel2() throws Throwable {

        // Arrange
        Hex hexA = new Hex();
        Hex hexB = new Hex();
        Hex hexC = new Hex();
        int tileId = 1;
        Tile tile = new Tile(tileId, hexA, hexB, hexC);
        Location location = new Location(0, 0, 0);

        adjacentToBoardTilePlacer.placeTile(tile, location);

        // Act
        nukeTilePlacer.placeTile(tile, location);

        // Assert
        int expectedBoardSize = 3;
        int expectedHexLevel = 2;
        Hex boardHex = board.getHex(location);

        Assert.assertEquals(expectedBoardSize, board.getSize());
        Assert.assertEquals(expectedHexLevel, boardHex.getLevel());
    }
}
