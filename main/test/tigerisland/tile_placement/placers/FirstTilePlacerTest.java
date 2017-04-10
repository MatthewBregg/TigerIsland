package tigerisland.tile_placement.placers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile.Tile;
import tigerisland.FirstTilePlacer;
import tigerisland.tile_placement.exceptions.InvalidTilePlacementException;

public class FirstTilePlacerTest {

    FirstTilePlacer firstTilePlacer;
    TilePlacement invalidTilePlacer;
    Board board;

    @Before
    public void setup() {

        board = new HexBoard();

        invalidTilePlacer = new InvalidTilePlacer();
        firstTilePlacer = new FirstTilePlacer(board);
        firstTilePlacer.setNextTilePlacement(invalidTilePlacer);
    }

    @Test
    public void test_ShouldAdd3HexesWhenBoardIsEmpty() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);

        // Act
        firstTilePlacer.placeTile(tile, location);

        // Assert
        int expectedHexesOnBoard = 3;
        Assert.assertEquals(expectedHexesOnBoard, board.getSize());
    }

    @Test
    public void test_ShouldAddHexesWithLevel1() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);

        // Act
        firstTilePlacer.placeTile(tile, location);

        // Assert
        int expectedHexLevel = 1;
        Hex mapHex = board.getHex(location);

        Assert.assertEquals(expectedHexLevel, mapHex.getLevel());
    }

    @Test (expected = InvalidTilePlacementException.class)
    public void test_ShouldCallNextPlacerWhenBoardIsNotEmpty() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);
        firstTilePlacer.placeTile(tile, location);

        Tile tile2 = new Tile();
        Location location2 = new Location(2,-1,-1);

        // Act
        firstTilePlacer.placeTile(tile2, location2);

        // Assert
        // Throws Exception
    }

    @Test (expected = InvalidTilePlacementException.class)
    public void test_ShouldCallNextPlacerWhenLocationIsNotZero() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, -1, 1);

        // Act
        firstTilePlacer.placeTile(tile, location);

        // Assert
        // Throws Exception
    }

}
