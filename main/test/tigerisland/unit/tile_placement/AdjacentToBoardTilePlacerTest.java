package tigerisland.unit.tile_placement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.tile.Tile;
import tigerisland.tile.TileHexFinder;
import tigerisland.tile_placement.*;

public class AdjacentToBoardTilePlacerTest {

    Board board;

    TilePlacement invalidTilePlacer;

    tigerisland.tile_placement.AdjacentToBoardTilePlacer adjacentToBoardTilePlacer;

    TileHexFinder tileHexLocationFactory;

    @Before
    public void setup() {

        board = new HexBoard();
        tileHexLocationFactory = new TileHexFinder();

        invalidTilePlacer = new InvalidTilePlacer();

        adjacentToBoardTilePlacer = new tigerisland.tile_placement.AdjacentToBoardTilePlacer(board, tileHexLocationFactory);
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
        int expectedHexesOnBoard = 3;
        Assert.assertEquals(expectedHexesOnBoard, board.getSize());
    }

}
