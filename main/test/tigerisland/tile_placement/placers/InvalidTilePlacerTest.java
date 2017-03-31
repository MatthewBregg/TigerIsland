package tigerisland.tile_placement.placers;

import org.junit.Test;
import tigerisland.board.Location;
import tigerisland.tile.Tile;
import tigerisland.tile_placement.exceptions.InvalidTilePlacementException;
import tigerisland.tile_placement.placers.InvalidTilePlacer;
import tigerisland.tile_placement.placers.TilePlacement;

public class InvalidTilePlacerTest {

    @Test(expected = InvalidTilePlacementException.class)
    public void test_ShouldThrowAnExceptionWhenPlacingTile() throws Throwable {

        //  Arrange
        TilePlacement invalidTilePlacer = new InvalidTilePlacer();
        Tile tile = new Tile();
        Location location = new Location(0, 0, 0);

        // Act
        invalidTilePlacer.placeTile(tile, location);
    }
}
