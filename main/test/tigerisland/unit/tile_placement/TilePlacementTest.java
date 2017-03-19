package tigerisland.unit.tile_placement;

import org.junit.Test;
import tigerisland.tile_placement.*;
import tigerisland.tile_placement.AdjacentToBoardTilePlacer;

public class TilePlacementTest {

    @Test
    public void test_ShouldConstructChain() {

        TilePlacement invalidTilePlacement = new InvalidTilePlacer();

        AdjacentToBoardTilePlacer adjacentToBoard = new AdjacentToBoardTilePlacer();
        adjacentToBoard.setNextTilePlacement(invalidTilePlacement);

        TilePlacement tilePlacementManager = new TilePlacementManager(adjacentToBoard);

    }
}
