package tigerisland.unit.tile_placement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tigerisland.*;
import tigerisland.tile_placement.*;

import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;


public class AdjacentToBoardTilePlacerTest {

    Board board;

    TilePlacement invalidTilePlacer;

    tigerisland.tile_placement.AdjacentToBoardTilePlacer adjacentToBoardTilePlacer;

    TilePlacement tilePlacementManager;

    TileHexLocationFactoryImp tileHexLocationFactory;

    @Before
    public void setup() {

        board = new HexBoard();
        tileHexLocationFactory = new TileHexLocationFactoryImp();

        invalidTilePlacer = mock(InvalidTilePlacer.class);

        adjacentToBoardTilePlacer = new tigerisland.tile_placement.AdjacentToBoardTilePlacer(board, tileHexLocationFactory);
        adjacentToBoardTilePlacer.setNextTilePlacement(invalidTilePlacer);

        tilePlacementManager = new TilePlacementManager(adjacentToBoardTilePlacer);
    }


    @Test
    public void test_ShouldAdd3HexesWhenBoardIsEmpty() {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);

        // Act
        tilePlacementManager.placeTile(tile, location);

        // Assert
        int expectedHexesOnBoard = 3;
        Assert.assertEquals(expectedHexesOnBoard, board.getSize());

    }

    @Test
    public void test_ShouldCallNextTilePlacerWhenIHexLocationIsUsed() {

        // Arrange
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);
        tilePlacementManager.placeTile(tile, location);

        Tile tile2 = new Tile();
        Location location2 = new Location(0, 0, 0);

        // Act
        tilePlacementManager.placeTile(tile2, location2);

        // Assert
        int expectedHexesOnBoard = 3;

        Assert.assertEquals(expectedHexesOnBoard, board.getSize());
        Mockito.verify(invalidTilePlacer, atMost(1) ).placeTile(tile2, location2);
    }

}
