package tigerisland.tile_placement.placers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tigerisland.board.Board;
import tigerisland.board.HexBoard;
import tigerisland.board.Location;
import tigerisland.hex.Hex;
import tigerisland.tile.Orientation;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.exceptions.InvalidTilePlacementException;
import tigerisland.tile_placement.placers.AdjacentToBoardTilePlacer;
import tigerisland.tile_placement.placers.FirstTilePlacer;
import tigerisland.tile_placement.placers.InvalidTilePlacer;
import tigerisland.tile_placement.placers.TilePlacement;

public class AdjacentToBoardTilePlacerTest {

    Board board;

    TilePlacement invalidTilePlacer;

    AdjacentToBoardTilePlacer adjacentToBoardTilePlacer;

    FirstTilePlacer firstTilePlacer;


    @Before
    public void setup() {

        board = new HexBoard();

        invalidTilePlacer = new InvalidTilePlacer();

        adjacentToBoardTilePlacer = new AdjacentToBoardTilePlacer(board);
        adjacentToBoardTilePlacer.setNextTilePlacement(invalidTilePlacer);

        firstTilePlacer = new FirstTilePlacer(board);
        firstTilePlacer.setNextTilePlacement(adjacentToBoardTilePlacer);
    }


    @Test
    public void test_ShouldAdd3HexesWhenBoardIsHasSome() throws Throwable {


        // Arrange
        Tile tile = new Tile();
        Location startLocation = new Location(0, 0,0);
        Location location = startLocation.getAdjacent(Orientation.getWest());
        location = location.getAdjacent(Orientation.getWest());

        // Prime the board with a tile
        firstTilePlacer.placeTile(new Tile(), startLocation);
        // Act
        adjacentToBoardTilePlacer.placeTile(tile, location);

        // Assert
        int expectedHexesOnBoard = 6;
        Assert.assertEquals(expectedHexesOnBoard, board.getSize());
    }

    @Test
    public void test_ShouldAdd3HexesWithLevel1() throws Throwable {

        // Arrange
        Tile tile = new Tile();
        Location startLocation = new Location(0, 0,0);
        Location location = startLocation.getAdjacent(Orientation.getWest());
        location = location.getAdjacent(Orientation.getWest());

        // Prime the board with a tile
        firstTilePlacer.placeTile(new Tile(), startLocation);
        // Act
        adjacentToBoardTilePlacer.placeTile(tile, location);


        // Assert
        int expectedHexLevel = 1;
        Hex mapHex = board.getHex(location);
        Assert.assertEquals(expectedHexLevel, mapHex.getLevel());
    }

    @Test (expected = InvalidTilePlacementException.class)
    public void test_ShouldThrowExceptionWhenEmptyBoard() throws Throwable {

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

    @Test
    public void test_AllowsPlacementForAllAdjacent() throws Throwable {
        // TODO mbregg create this test.

    }

    @Test (expected = InvalidTilePlacementException.class)
    public void test_DisallowsPlacementIfNoAdjacent() throws Throwable {
        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);
        firstTilePlacer.placeTile(tile, location);

        Tile tile2 = new Tile();
        Location location2 = new Location(42, 42, -42*2);

        // Act
        adjacentToBoardTilePlacer.placeTile(tile2, location2);

        // Assert
        // Throws exception
    }


}
