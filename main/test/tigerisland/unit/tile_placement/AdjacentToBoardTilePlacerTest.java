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

public class AdjacentToBoardTilePlacerTest {

    Board board;

    TilePlacement invalidTilePlacer;

    tigerisland.tile_placement.AdjacentToBoardTilePlacer adjacentToBoardTilePlacer;


    @Before
    public void setup() {

        board = new HexBoard();

        invalidTilePlacer = new InvalidTilePlacer();

        adjacentToBoardTilePlacer = new tigerisland.tile_placement.AdjacentToBoardTilePlacer(board);
        adjacentToBoardTilePlacer.setNextTilePlacement(invalidTilePlacer);
    }


    @Test
    public void test_ShouldAdd3HexesWhenBoardIsEmpty() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);

        // Act
        adjacentToBoardTilePlacer.placeTile(tile, location);

        // Assert
        int expectedHexesOnBoard = 3;
        Assert.assertEquals(expectedHexesOnBoard, board.getSize());
    }

    @Test
    public void test_ShouldAdd3HexesWithLevel1() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);

        // Act
        adjacentToBoardTilePlacer.placeTile(tile, location);

        // Assert
        int expectedHexLevel = 1;
        Hex mapHex = board.getHex(location);
        Assert.assertEquals(expectedHexLevel, mapHex.getLevel());
    }

    @Test (expected = InvalidTilePlacementException.class)
    public void test_ShouldThrowExceptionWhenNextTilePlacerIsCalled() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);
        adjacentToBoardTilePlacer.placeTile(tile, location);

        Tile tile2 = new Tile();
        Location location2 = new Location(0, 0, 0);

        // Act
        adjacentToBoardTilePlacer.placeTile(tile2, location2);

        // Assert
        // Throws exception
    }

}
